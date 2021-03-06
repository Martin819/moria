#!/bin/bash

# Deploys services on top of deployed k8s cluster
# Don't forget to aquire your credentials first (gcloud container clusters get-credentials <cluster-name> --zone <zone> --project <project>)

# Usage example: bash deploy_server.sh db-cluster <root-pass> userdb <userpass> moria db-cluster-node prometheus-grafana-1 80 3000 grafana-node

xtradb_cluster_name=$1
mysql_root_password=$2
mysql_user_name=$3
mysql_user_password=$4
mysql_database=$5
xtradb_node_name=$6
grafana_cluster_name=$7
grafana_cluster_port=$8
grafana_cluster_target_port=$9
grafana_node_name=${10}

echo "##### Install Helm #####"
wget https://storage.googleapis.com/kubernetes-helm/helm-v2.6.2-linux-amd64.tar.gz
tar zxfv helm-v2.6.2-linux-amd64.tar.gz
cp linux-amd64/helm .
kubectl create clusterrolebinding user-admin-binding --clusterrole=cluster-admin --user=$(gcloud config get-value account)
kubectl create serviceaccount tiller --namespace kube-system
kubectl create clusterrolebinding tiller-admin-binding --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
kubectl create clusterrolebinding --clusterrole=cluster-admin --serviceaccount=default:default concourse-admin
./helm init --service-account=tiller
./helm update
echo "##### Wait for Tiller account to get ready #####"
sleep 20
echo "Please check if both server and client are the same version:"
./helm version

echo "##### Install XtraDB cluster #####"
./helm install --name ${xtradb_cluster_name} --set mysqlRootPassword=${mysql_root_password},mysqlUser=${mysql_user_name},mysqlPassword=${mysql_user_password},mysqlDatabase=${mysql_database} stable/percona-xtradb-cluster
sleep 10

echo "##### Expose XtraDB node #####"
kubectl expose service ${xtradb_cluster_name}-pxc --port=3306 --target-port=3306 --name=${xtradb_node_name} --type="LoadBalancer"

echo "##### Expose Prometheus-Galera node #####"
kubectl expose service ${grafana_cluster_name} --port=${grafana_cluster_port} --target-port=${grafana_cluster_target_port} --name=${grafana_node_name} --type="LoadBalancer"

echo "##### Waiting for nodes to aquire External IPs #####"
sleep 1m
kubectl get services | grep ${xtradb_node_name}
kubectl get services | grep ${grafana_node_name}

echo "##### Grant privilegies to MySQL users #####"
kubectl exec --namespace default -ti db-cluster-pxc-0 -c database -- mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '${mysql_root_password}';"
kubectl exec --namespace default -ti db-cluster-pxc-0 -c database -- mysql -e "GRANT ALL PRIVILEGES ON *.* TO '${mysql_user_name}'@'%' IDENTIFIED BY '${mysql_user_password}';"
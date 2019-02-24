import React, { Component } from 'react';
import { Card, CardBody, CardText, CardLink, CardTitle, Spinner } from 'reactstrap';
import { AreaChart, Area, CartesianGrid, XAxis, YAxis, Tooltip, Legend } from 'recharts';

const CustomAreaChart = ({ chartData, isLoading }) => {
  return (
    <div className="ml-3">
      <Card style={{ maxWidth: '35rem' }}>
        <CardBody className="text-center" style={{ marginBottom: '-40px' }}>
          {!isLoading && chartData.length > 0 ? (
            <AreaChart width={500} height={220} data={chartData} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
              <defs>
                <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8} />
                  <stop offset="95%" stopColor="#8884d8" stopOpacity={0} />
                </linearGradient>
                <linearGradient id="colorPv" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#82ca9d" stopOpacity={0.8} />
                  <stop offset="95%" stopColor="#82ca9d" stopOpacity={0} />
                </linearGradient>
              </defs>
              <XAxis dataKey="name" />
              <YAxis />
              <CartesianGrid strokeDasharray="3 3" />
              <Tooltip />
              <Area
                isAnimationActive={true}
                type="monotone"
                dataKey="uv"
                stroke="#8884d8"
                fillOpacity={1}
                fill="url(#colorUv)"
              />
              <Area
                isAnimationActive={true}
                type="monotone"
                dataKey="pv"
                stroke="#82ca9d"
                fillOpacity={1}
                fill="url(#colorPv)"
              />
            </AreaChart>
          ) : (
            <Spinner type="grow" color="primary" />
          )}
        </CardBody>
        <CardBody>
          <hr />
          <CardTitle>Test title</CardTitle>
          <CardText>
            Some quick example text to build on the card title and make up the bulk of the card's content.
          </CardText>
        </CardBody>
      </Card>
    </div>
  );
};

export default CustomAreaChart;

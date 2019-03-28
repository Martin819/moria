import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const CustomBarChart = props => {
  const { barChartData } = props;
  return (
    <div style={{ width: '100%', height: 300 }}>
      <ResponsiveContainer>
        <BarChart data={barChartData} margin={{ top: 5, right: 50, left: 20, bottom: 5 }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis tickFormatter={yAxisFormatter} />
          <Tooltip formatter={tooltipValueFormatter} />
          <Legend
            verticalAlign="bottom"
            layout="horizontal"
            align="center"
            wrapperStyle={{
              paddingLeft: '10px'
            }}
          />
          <Bar dataKey="Incomes" fill="#71ae1e" />
          <Bar dataKey="Expenses" fill="#cc3c29" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

const yAxisFormatter = value => value.toLocaleString('cs-cz');

const tooltipValueFormatter = value =>
  value.toLocaleString('cs-cz', {
    style: 'currency',
    currency: 'CZK'
  });

export default CustomBarChart;

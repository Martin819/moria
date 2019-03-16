import React, { Component } from 'react';
import { PieChart, Pie, Sector, Cell, ResponsiveContainer, Legend } from 'recharts';
import { COLORS } from '../../constants/colors';

const graphColors = COLORS;

const RADIAN = Math.PI / 180;
const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
  const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
  const x = cx + radius * Math.cos(-midAngle * RADIAN);
  const y = cy + radius * Math.sin(-midAngle * RADIAN);

  return (
    <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} dominantBaseline="central">
      {`${(percent * 100).toFixed(0)}%`}
    </text>
  );
};

class CustomPieChart extends Component {
  render() {
    console.log(this.props);
    const data = this.props.chartData;
    return (
      //   <ResponsiveContainer width="99%" height="100%">
      <ResponsiveContainer width={500} height={300}>
        <PieChart margin={{ top: 0, right: 0, left: 50, bottom: 0 }}>
          <Pie data={data} labelLine={false} label={renderCustomizedLabel} dataKey="value">
            {data.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={graphColors[index % graphColors.length]} />
            ))}
          </Pie>
          <Legend verticalAlign="middle" align="left" height={100} layout="vertical" />
        </PieChart>
      </ResponsiveContainer>
    );
  }
}

export default CustomPieChart;

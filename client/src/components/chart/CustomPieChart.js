import React, { Component } from 'react';
import { PieChart, Pie, Cell, Legend } from 'recharts';
import { COLORS } from '../../constants/colors';
import { isEqual } from 'lodash';

const graphColors = COLORS;

const RADIAN = Math.PI / 180;
const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent }) => {
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
  shouldComponentUpdate(nextProps) {
    return !isEqual(this.props.chartData, nextProps.chartData);
  }

  render() {
    const { chartData } = this.props;
    return (
      <PieChart width={500} height={300} margin={{ top: 0, right: 0, left: 20, bottom: 0 }}>
        <Pie data={chartData} labelLine={false} label={renderCustomizedLabel} dataKey="value">
          {chartData.map((entry, index) => (
            <Cell key={`cell-${index}`} fill={graphColors[index % graphColors.length]} />
          ))}
        </Pie>
        <Legend verticalAlign="middle" align="left" height={300} layout="vertical" />
      </PieChart>
    );
  }
}

export default CustomPieChart;

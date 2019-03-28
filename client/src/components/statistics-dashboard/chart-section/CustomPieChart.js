import React, { Component } from 'react';
import { PieChart, Pie, Cell, Legend, ResponsiveContainer, Tooltip } from 'recharts';
import { COLORS_INCOME, COLORS_EXPENSES } from '../../../constants/colors';
import { isEqual } from 'lodash';
import { TransactionDirections } from '../../../constants/transactions';

const RADIAN = Math.PI / 180;
const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent }) => {
  const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
  const x = cx + radius * Math.cos(-midAngle * RADIAN);
  const y = cy + radius * Math.sin(-midAngle * RADIAN);
  if (percent > 0.07) {
    return (
      <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} dominantBaseline="central">
        {`${(percent * 100).toFixed(0)} % `}
      </text>
    );
  }
};

class CustomPieChart extends Component {
  shouldComponentUpdate(nextProps) {
    return !isEqual(this.props.chartData, nextProps.chartData);
  }

  render() {
    const { chartData, direction, sum } = this.props;
    const activeColorPalette = direction === TransactionDirections.INCOMING.id ? COLORS_INCOME : COLORS_EXPENSES;
    const chartDataWithPct = chartData.map(data => Object.assign({}, data, { pct: data.value / sum }));
    return (
      <div style={{ width: '100%', height: 300 }}>
        <ResponsiveContainer>
          <PieChart margin={{ top: 0, right: 0, left: 0, bottom: 0 }}>
            <Pie data={chartDataWithPct} labelLine={false} label={renderCustomizedLabel} dataKey="value">
              {chartData.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={activeColorPalette[index % activeColorPalette.length]} />
              ))}
            </Pie>
            <Legend verticalAlign="middle" align="left" height={300} layout="vertical" />
            <Tooltip formatter={tooltipFormatter} />
          </PieChart>
        </ResponsiveContainer>
      </div>
    );
  }
}

const tooltipFormatter = (value, name, props) => {
  const percent = props.payload.pct;
  return `${value.toLocaleString('cs-cz', {
    style: 'currency',
    currency: 'CZK'
  })} (${(percent * 100).toFixed(0)} %)`;
};

export default CustomPieChart;

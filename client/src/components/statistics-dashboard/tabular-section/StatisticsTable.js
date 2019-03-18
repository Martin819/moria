import React from 'react';
import { Table } from 'reactstrap';

class StatisticsTable extends React.Component {
  render() {
    return (
      <Table responsive>
        <thead>
          <tr>
            <th>Category</th>
            <th className="text-right">Amount</th>
          </tr>
        </thead>
        <tbody>
          {this.props.tableData.map((entry, index) => {
            return (
              <tr key={`Tr_${index}`}>
                <td key={`Td_${index}_1`} scope="row">
                  {entry.name}
                </td>
                <td key={`Td_${index}_2`} className="text-right">
                  {entry.value.toLocaleString('cs-cz', {
                    style: 'currency',
                    currency: 'CZK'
                  })}
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    );
  }
}

export default StatisticsTable;

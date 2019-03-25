import React from 'react';
import { Table } from 'reactstrap';
import { Grow } from '@material-ui/core/';

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
              <Grow key={index} in={true} timeout={500 + 150 * index}>
                <tr>
                  <td scope="row">{entry.name}</td>
                  <td key={`Td_${index}_2`} className="text-right">
                    {entry.value.toLocaleString('cs-cz', {
                      style: 'currency',
                      currency: 'CZK'
                    })}
                  </td>
                </tr>
              </Grow>
            );
          })}
        </tbody>
      </Table>
    );
  }
}

export default StatisticsTable;

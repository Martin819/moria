import React, { Fragment } from 'react';
import ChartContainer from '../../containers/chart/ChartContainer';

const ChartsDashboard = () => {
  return (
    <Fragment>
      <h1>Charts</h1>
      <div className="row mt-5">
        <ChartContainer />
        {/* <ChartContainer /> */}
      </div>
    </Fragment>
  );
};

export default ChartsDashboard;

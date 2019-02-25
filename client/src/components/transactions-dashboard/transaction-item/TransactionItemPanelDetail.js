import React from 'react';
import {
  ExpansionPanelDetails,
  Grid,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  OutlinedInput
} from '@material-ui/core';
import { Button } from 'reactstrap';

const TransactionItemPanelDetail = props => {
  return (
    <ExpansionPanelDetails>
      <Grid container direction="column">
        <Grid item>
          <Typography>
            Message: {props.message} <br /> ID: {props.id}
          </Typography>
        </Grid>
        <Grid item>
          <FormControl style={{ minWidth: 120 }}>
            <InputLabel htmlFor="category">Category</InputLabel>
            <Select value="" inputProps={{ name: 'Category', id: 'category' }}>
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value={10}>Electronics</MenuItem>
              <MenuItem value={20}>Food</MenuItem>
              <MenuItem value={30}>Home equipment</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item>
          <hr />
          <Button outline color="success" type="submit">
            Detail
          </Button>
        </Grid>
      </Grid>
    </ExpansionPanelDetails>
  );
};

export default TransactionItemPanelDetail;

import React from 'react';

import { styled } from '@mui/material/styles';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';


function screenScheduleItem(props) {
    const { screenSchedule, handleChangeSalePolicy } = props;

    

    return (
        <>
            
            <StyledTableCell component="th" scope="row">
                {screenSchedule.screenScheduleId}
            </StyledTableCell>
            <StyledTableCell align="right">{screenSchedule.theaterName}</StyledTableCell>
            <StyledTableCell align="right">{screenSchedule.theaterFloor} 층</StyledTableCell>
            <StyledTableCell align="right">{screenSchedule.startTime}</StyledTableCell>
            <StyledTableCell align="right">{screenSchedule.endTime}</StyledTableCell>

            
            <StyledTableCell align="right">

                <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">정책할인</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    // value={screenSchedule.salePolicyName===null ? }
                    defaultValue={screenSchedule.salePolicyName}
                    label="할인 정책"
                    onChange={(event) => {handleChangeSalePolicy(screenSchedule, event.target.value)}}
                    >
                        <MenuItem value={"null"}>없음</MenuItem>
                        <MenuItem value={"정액할인"}>정액할인</MenuItem>
                        <MenuItem value={"정률할인"}>정률할인</MenuItem>
                    </Select>
                </FormControl>

            </StyledTableCell>     

        </>
    );
}

export default screenScheduleItem;

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: theme.palette.common.black,
      color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
      fontSize: 14,
    },
  }));
  

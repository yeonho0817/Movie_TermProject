import React from 'react';
import ScreenScheduleItem from './screenScheduleItem'
import styles from './screenSchedule.module.css'

import Paper from '@mui/material/Paper';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import { styled } from '@mui/material/styles';
import axios from 'axios';

function ScreenSchedule(props) {
    const { screenScheduleList, setScreenScheduleList } = props;

    const handleChangeSalePolicy = async (screenSchedule, value) => {
        const data = {
            salePolicyName:value,
            screenScheduleId:screenSchedule.screenScheduleId
        }
        
        
        await axios.put("http://localhost:8080/salePolicy/apply",data).then((res) => {
            console.log(res.data);

            const findIndex = screenScheduleList.findIndex(element => element.screenScheduleId === screenSchedule.screenScheduleId);

            let copyArray = [...screenScheduleList];

            if(findIndex !== -1) {
                copyArray[findIndex] = {...copyArray[findIndex], salePolicyName:value, salePolicyValue:null};
            }

            setScreenScheduleList(copyArray);

            alert(res.data.message);
        })
        
        
        
      };

    return (
        <div className={styles.table_container}>
            <TableContainer component={Paper}>
                <Table sx={{ width: 900,  margin:"0px auto" }} aria-label="customized table">
                    <TableHead>
                    <TableRow>
                        <StyledTableCell>번호</StyledTableCell>
                        <StyledTableCell align="right">상영관</StyledTableCell>
                        <StyledTableCell align="right">층 수</StyledTableCell>
                        <StyledTableCell align="right">시작시간</StyledTableCell>
                        <StyledTableCell align="right">종료시간</StyledTableCell>
                        <StyledTableCell align="right">할인정책</StyledTableCell>
                    </TableRow>
                    </TableHead>

                    <TableBody>
                   
                        {
                            screenScheduleList.map((screenSchedule) => (
                                <StyledTableRow key={screenSchedule.screenScheduleId}>
                                    <ScreenScheduleItem key={screenSchedule.screenScheduleId} handleChangeSalePolicy={handleChangeSalePolicy}  screenSchedule={screenSchedule} />
                                </StyledTableRow>
                            ))
                        }

                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default ScreenSchedule;

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: theme.palette.common.black,
      color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
      fontSize: 14,
    },
  }));

  const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
    // hide last border
    '&:last-child td, &:last-child th': {
      border: 0,
    },
  }));
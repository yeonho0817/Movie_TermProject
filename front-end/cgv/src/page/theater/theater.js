import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Header from '../../component/homeComponent/header';
import DayItem from '../../component/theaterComponent/dayItem';
import TimeTable from '../../component/theaterComponent/timeTable';
import styles from './theater.module.css';

const Theater = () => {

    const [theaterInfo,setTheaterInfo] = useState('');

    const getTheaterInfo = async()=> {
        const res = await (await axios.get('http://localhost:8080/screenSchedule')).data;


        setTheaterInfo(res.data[0]);
    }

    useEffect(()=> {
        getTheaterInfo();
    },[])

    return (
        <>
            <Header/>
            <div className={styles.container}>
                <div className={styles.contents}>
                    <div className={styles.main}>
                        <div className={styles.scheduleBox}>
                            <div className={styles.slider}>
                                <ul>
                                    <DayItem/>
                                </ul>
                            </div>
                        </div>
                        <div className={styles.timeTableBox}>
                            <ul>
                                {/* {schedule.map((item)=> <TimeTable key={item.id} item={item}/>)} */}
                                {theaterInfo && 
                                    <TimeTable theaterInfo={theaterInfo}/>
                                } 

                            </ul>

                        </div>
                    </div>
                </div>        
            </div>
        </>
    );
};

export default Theater;
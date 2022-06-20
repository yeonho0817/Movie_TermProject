import React from 'react';
import styles from './theaterInfos.module.css';
import TimeItem from './timeItem';

const TheaterInfos = (props) => {

    const {info, idx, settingScheduleId,selectScheduleId, setOnSeatBtn} = props;
    console.log("띠더인포ssss:",info);

    return (
        <div className={styles.theater}>
            <span className={styles.title}>
                <span className={styles.floor}>{idx+1}관 {idx+1}층</span>
                <span className={styles.seatCount}>(총20석)</span>
            </span>
            <ul className={styles.titmeTableBox}>
            {
                info &&
                    info.map((time) => <TimeItem time={time} screenScheduleId={time.screenScheduleId} 
                    selectScheduleId={selectScheduleId} settingScheduleId={settingScheduleId}
                    setOnSeatBtn={setOnSeatBtn} isClosed={time.isClosed}
                    />)
            }
            
            </ul>
        </div>
    );
};

export default TheaterInfos;
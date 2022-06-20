
import React from 'react';
import styles from './timeItem.module.css';

const TimeItem = (props) => {

    const {time, settingScheduleId , selectScheduleId, screenScheduleId, setOnSeatBtn,isClosed} = props;

    const times = time.startTime.split(' ');

    
    return (
        <>
            {
                isClosed ? 
                    <div className={`${styles.timeBox} ${selectScheduleId === screenScheduleId ? styles.seleted : null} ${styles.closed} `}
                    
                        // onClick={()=> console.log("선택된 스크린스케줄아이디 : ", screenScheduleId)}
                    >
                        <span className={styles.time}>
                            <span>{times[1]}</span>
                            {/* <span>{time}</span> */}
                        </span>
                        <span className={styles.count}>잔여: {time.remainSeat}</span>
                    </div>
                :
                    <div className={`${styles.timeBox} ${selectScheduleId === screenScheduleId ? styles.seleted : null} ${styles.on}`}
                        onClick={()=> {
                            settingScheduleId(screenScheduleId);
                            setOnSeatBtn(true);
                        }}
                        // onClick={()=> console.log("선택된 스크린스케줄아이디 : ", screenScheduleId)}
                    >
                        <span className={styles.time}>
                            <span>{times[1]}</span>
                            {/* <span>{time}</span> */}
                        </span>
                        <span className={styles.count}>잔여: {time.remainSeat}</span>
                    </div>
            }
        </>
    
    );
};

export default TimeItem;
import React from 'react';
import ShowTime from './showTime';
import styles from './timeTable.module.css';

const TimeTable = (props) => {

    const {theaterInfo} = props;
    console.log("타임테이블::",theaterInfo);
    
    const oneFloor = theaterInfo.readScreenScheduleInfoDTOList.filter(item=> item.theaterFloor === 1);
    const twoFloor = theaterInfo.readScreenScheduleInfoDTOList.filter(item=> item.theaterFloor === 2);

    const readTheater = [oneFloor,twoFloor];

    

    return (
        <div className={styles.cotainer}>
            <div className={styles.times}>
                <div className={styles.infoMovie}>
                    <span className={styles.title}>{theaterInfo.title}</span>
                    <span>{theaterInfo.genre}</span>
                    <span>{theaterInfo.ageLimit}세 이용가</span>
                    <span>{theaterInfo.runTime}</span>
                    <span>{theaterInfo.openingTime} 개봉</span>
                </div>
                    {readTheater.map((item)=> <ShowTime key={item.screenScheduleId} item={item} />)}
                    
                    

            </div>
        </div>
    );
};

export default TimeTable;
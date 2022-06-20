import React from 'react';
import styles from './dayItem.module.css';

const DayItem = () => {

    // const {changeDate} = props;

    return (
        // onClick={()=> changeDate(date)}
        <div className={styles.box}>
            <div className={styles.day}>
                <span>06월</span>
                <strong>14</strong>
            </div>
        </div>
    );
};

export default DayItem;
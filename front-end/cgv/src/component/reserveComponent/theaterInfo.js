import React from 'react';
import styles from './theaterTime.module.css';
import TimeItem from './timeItem';

const TheaterInfo = () => {
    return (
        <div className={styles.theater}>
            <span className={styles.title}>
                <span className={styles.floor}>1관 1층</span>
                <span className={styles.seatCount}>(총158석)</span>
            </span>
            <ul className={styles.titmeTableBox}>
                <TimeItem/>
            </ul>
        </div>
    );
};

export default TheaterInfo;
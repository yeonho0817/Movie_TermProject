import React from 'react';
import styles from './seatItem.module.css';

const SeatItem = (props) => {
    
    const {seatInfo, handleSelectedSeat, selectedSeatList} = props;
    
    console.log(seatInfo);

    return (
        <div className={`${styles.seatBox} ${selectedSeatList.includes(seatInfo.id) ? styles.choiced : null }`  }
            onClick={()=> handleSelectedSeat(seatInfo.id,seatInfo.isUsing)}
        >
            <div className={(seatInfo.isUsing) ? styles.disable:styles.active}>
                <span className={styles.row}>{seatInfo.rowNumber}행 </span>
                <span className={styles.col}>{seatInfo.colNumber}열 좌석</span>
            </div>
        </div>
    );
};

export default SeatItem;
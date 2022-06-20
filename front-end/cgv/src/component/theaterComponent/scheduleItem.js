import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './scheduleItem.module.css';
import MonetizationOnOutlinedIcon from '@mui/icons-material/MonetizationOnOutlined';

const ScheduleItem = (props) => {
    
    const navige = useNavigate();
    const {time,seat, scheduleId, salePolicyName, isClosed} = props;
    
    console.log("스케쥴아이템에 상영아이디?",scheduleId);

    const times = time.split(' ');

    const handleClick = () => {
        navige('/reserve', {
            state: {
                beforeScheduleId: scheduleId
            }
        })
    }

    return (
        <>
            {
                isClosed ?
                    <div className={`${styles.box} ${styles.closed}`}>
                        {
                            salePolicyName===null ?
                            null
                            :
                            <div className={styles.sale_icon}>
                                <MonetizationOnOutlinedIcon sx={{color:"red"}} />
                            </div>
                        }
                        
                        <div>
                            {/* <span>{time}</span> */}
                            <span className={styles.time}>{times[1]}</span>
                        </div>
                        <div>
                            {/* <span>{restSeats}석</span> */}
                            <span className={styles.seat}>{seat}석</span>
                        </div>
                        </div>
                    :
                        <div className={`${styles.box} ${styles.on}`}
                        onClick={()=> handleClick()}
                        >
                            {
                                salePolicyName===null ?
                                null
                                :
                                <div className={styles.sale_icon}>
                                    <MonetizationOnOutlinedIcon sx={{color:"red"}} />
                                </div>
                            }
                                
                            <div>
                                {/* <span>{time}</span> */}
                                <span className={styles.time}>{times[1]}</span>
                            </div>
                            <div>
                                {/* <span>{restSeats}석</span> */}
                                <span className={styles.seat}>{seat}석</span>
                            </div>
                        </div>
            }
            
        </>
    );
};

export default ScheduleItem;
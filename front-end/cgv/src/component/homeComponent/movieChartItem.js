import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import styles from './movieChartItem.module.css';

const MovieChartItem = (props) => {

    const {movie} = props;
    const navigate = useNavigate();
    const [btnVison,setBtnVison] = useState(false);

    return (
        <div className={styles.itemBox} 
            onMouseEnter={()=> setBtnVison(true)}
            onMouseLeave={()=> setBtnVison(false)}
        >
            <div className={styles.imgBox}>
                <img className={btnVison ? styles.imgOpacity : styles.img} src={movie.image} alt="이미지"/>
                
                <div className={`${styles.btnBox} ${btnVison ? styles.vision : styles.hide}`}>
                    <Button className={styles.btn} onClick={()=> navigate(`/movies/detail/${movie.id}`)}>상세보기</Button>
                    <Link to="/reserve"><Button className={styles.btn}>예매하기</Button></Link>
                </div>
            </div>
            <div className={styles.movieInfo}>
                <strong className={styles.movieName}>{movie.title}</strong>
                <span>예매율 {movie.ticketingRate}%</span>
            </div>

        </div>
    );
};

export default MovieChartItem;
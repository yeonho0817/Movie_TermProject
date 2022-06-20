import React from 'react';
import styles from './movieItem.module.css';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';

const MovieItem = (props) => {

    const {movie, idx} = props;

    const navigate = useNavigate();



    return (
        <div className={styles.itemBox}>
            <div className={styles.imgBox}>
                <strong className={styles.rank}>No.{idx+1}</strong>
                <span className={styles.thumbImg} onClick={()=> navigate(`/movies/detail/${movie.id}`)}>
                    <img src={movie.image} alt="포스터" />
                    {/* <span>12세등급?</span> */}
                </span>
            </div>
            <div className={styles.contentsBox}>
                <Link to='/'><strong>{movie.title}</strong></Link>
                <div className={styles.score}>
                    <strong className={styles.percent}>
                        예매율
                        <span>{movie.ticketingRate}%</span>
                    </strong>
                </div>
                <div className={styles.openDateBox}>
                    <strong>{movie.openingTime} 개봉</strong>
                </div>
                <div className={styles.reserveLink}>
                    <Link to='/'>
                        <Button>예매하기</Button>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default MovieItem;
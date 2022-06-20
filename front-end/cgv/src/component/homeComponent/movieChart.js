
import React from 'react';
import { Link } from 'react-router-dom';
import styles from './movieChart.module.css';
import MovieChartItem from './movieChartItem';

const MovieChart = (props) => {

    const {chartMovies , pageNum, setPageNum,getPageMovies} = props;

    console.log("무비차트::",chartMovies);

    const handleBtnNext = () => {
        let currentPageNum = pageNum+1;
        setPageNum(currentPageNum);
        getPageMovies(currentPageNum);
    }
    const handleBtnPrev = () => {
        let currentPageNum = pageNum-1;
        setPageNum(currentPageNum);
        getPageMovies(currentPageNum);
    }

    


    return (
        <div className={styles.movieChartBox}>
            <div className={styles.contents}>
                <div className={styles.tabBox}>
                    <div className={styles.tab}>
                        <h3>
                            <span className={styles.tabTitle}>무비차트</span>
                        </h3>
                    </div>
                    <Link to="/movies" className={styles.viewAll}>전체보기</Link>
                </div>
                <div className={styles.movieCharBox}>
                    <div className={styles.swipBox}>
                        {
                            chartMovies.map((item) => (
                                <MovieChartItem key={item.id} movie={item} />
                            ))
                        }
                        
                    </div>
                    {
                        pageNum !== 0 &&
                            <div className={styles.btnPrev}
                                onClick={()=> handleBtnPrev()}
                            />
                    }
                    {
                        pageNum !== 2 &&
                            <div className={styles.btnNext}
                                onClick={()=> handleBtnNext()}
                            />
                    }
                </div>
            </div>
        </div>
    );
};

export default MovieChart;
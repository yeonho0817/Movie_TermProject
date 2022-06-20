import { TextField } from '@mui/material';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import MovieItem from '../../component/allMovieComponent/movieItem';
import Header from '../../component/homeComponent/header';
import styles from './allMovie.module.css';

const AllMovies = () => {

    const [movies,setMovies] = useState([]);

    const [sortKind, setSortKind] = useState('1');
    const [serchKind,setSerchKind]  = useState('title')
    const [serch,setSerch] = useState('');
    
    const handleSerch = async () => {
        if(serch) {
            let res;
            if(sortKind === '1') {
                res = await (await axios.get(`http://localhost:8080/movie/ticketingRating`, {
                    params: {
                        [serchKind] : serch
                    }
                })).data;
        
                
            }else {
                res = await (await axios.get(`http://localhost:8080/movie/rating`, {
                    params: {
                        [serchKind] : serch
                    }
                })).data;
            }
            if(res.data.length<1) {
                alert("해당 영화제목 또는 배우명과 일치하는 영화가 없습니다.");
            } else {
                setMovies(res.data);
            }
            setSerch('');
        }else {
            alert("검색창에 입력이 필요합니다.");
        }
        
    }
    
    const getMovies = async () => {
        const res = await (await axios.get('http://localhost:8080/movie/ticketingRating')).data;

        console.log("전체조회::", res.data);

        setMovies(res.data);
    }
    const getsortMovies = async () => {
        let res;
        if(sortKind === '1') {
            // 예매율순으로 조회
            res = await (await axios.get('http://localhost:8080/movie/ticketingRating')).data;
            
        }else {
            // 평점순으로 조회
            res = await (await axios.get('http://localhost:8080/movie/rating')).data;
        }
        setMovies(res.data);
    }


    useEffect(()=> {
        getMovies();
    },[])

    return (
        <>
            <Header/>
            <div className={styles.container}>
                <div className={styles.contents}>
                    <div className={styles.movieChartBox}>
                        <div className={styles.headBox}>
                            <h3>무비차트</h3>
                        </div>
                    </div>
                    <div className={styles.sortBox}>
                        <div className={styles.serchBox}>
                            <select onChange={(e)=> setSerchKind(e.target.value)}>
                                <option title="현재 선택됨" selected="" value="title">영화제목</option>
                                <option title="현재 선택됨" selected="" value="actorName">영화배우</option>
                            </select>
                            <TextField className={styles.inputSerch} value={serch} label="영화제목, 배우등을 검색하세요" variant="outlined"
                                onChange={(e)=> setSerch(e.target.value)}
                            />
                            <Button className={styles.btn} onClick={()=>handleSerch()}>검색하기</Button>
                        </div>
                        <div>
                            <select onChange={(e)=> setSortKind(e.target.value)}>
                                <option title="현재 선택됨" selected="" value="1">예매율순</option>
                                <option title="현재 선택됨" selected="" value="2">평점순</option>
                            </select>
                            <Button className={styles.btn} onClick={()=> getsortMovies()} >Go</Button>
                        </div>

                    </div>
                    <div className={styles.movieListBox}>
                        {
                            movies &&
                                movies.map((movie,idx) => <MovieItem key={movie.id} idx={idx} movie={movie} />)
                        }
                    </div>
                </div>
            </div>
            
        </>
    );
};

export default AllMovies;
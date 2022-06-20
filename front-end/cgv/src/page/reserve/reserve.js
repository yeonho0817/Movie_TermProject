import React, { useEffect, useState } from 'react';
import Header from '../../component/homeComponent/header';
import styles from './reserve.module.css';
import { BsFillSunriseFill,BsFillMoonStarsFill } from 'react-icons/bs';

import { Button } from 'react-bootstrap';
import axios from 'axios';
import TheaterInfos from '../../component/reserveComponent/theaterInfos';
import { useLocation, useNavigate } from 'react-router-dom';
import SeatItem from '../../component/reserveComponent/seatItem';
import {isLogin} from '../login/login'



const Reserve = () => {

    const navigete = useNavigate();

    //const {beforeScheduleId} = useLocation().state;
    const {state} = useLocation();
    

    const [selectMovie,setSelectMovie] = useState('범죄도시2');
    const [selectScheduleId,setSelectScheduleId] = useState(state ? state.beforeScheduleId : '');

    const [onSeatBtn,setOnSeatBtn] = useState(selectScheduleId ? true : false);
    const [seatSelect,setSeatSelect] = useState(false);
    const [peopleCount,setPeopleCount] = useState(1);

    const [theaterInfo,setTheaterInfo] = useState('');
    const [remainInfo,setRemainInfo] = useState('');

    const [allPrice,setAllPrice] = useState('');
    const [selectedSeatList,setSelectedSeatList] = useState([]);

    const oneFloor = theaterInfo ? theaterInfo.readScreenScheduleInfoDTOList.filter(item=> item.theaterFloor === 1) : null;
    const twoFloor = theaterInfo ? theaterInfo.readScreenScheduleInfoDTOList.filter(item=> item.theaterFloor === 2) : null;

    const readTheater = [oneFloor,twoFloor];

    const [seatA,setSeatA] = useState('');
    const [seatB,setSeatB] = useState('');

    console.log("read띠더",readTheater);

    const settingScheduleId = (id) => {
        setSelectScheduleId(id);
        // getRemainSeat(id); 이거를 좌선석택시 
    }

    const handleSeatBtnClick = () => {
        if(isLogin()) {
            getRemainSeat(selectScheduleId);
            setSeatSelect(true);
        }else {
            alert("로그인 후 이용 가능합니다.");
            navigete('/login')
        }
    }

    const getRemainSeat = async(id)=> {
        const res = await (await axios.get(`http://localhost:8080//ticketing/remainSeat/${id}`)).data;
        setRemainInfo(res.data)
        setAllPrice(res.data.price)

        setSeatA(res.data.seatColDTOS[0].readSeatDTOList);
        console.log("좌석표시좀",res.data.seatColDTOS[0].readSeatDTOList);
        setSeatB(res.data.seatColDTOS[1].readSeatDTOList);
    }

    const getTheaterInfo = async()=> {
        const res = await (await axios.get('http://localhost:8080/screenSchedule')).data;

        console.log("api theat더요청",res.data[0])

        setTheaterInfo(res.data[0]);
        setSelectMovie(res.data[0].tile);
    }
    const handleBeloginReserve = () => {
        alert("로그인 후 이용 가능합니다.");
        navigete('/login')
    }

    const handleSelectedSeat = (seatId,isUsing) => {
        if(isUsing) {
            return alert("선택불가 좌석입니다.")
        }
        if(selectedSeatList.length>=peopleCount) {
            return alert("최대인원이 선택되었습니다.")
        }
        if(selectedSeatList.includes(seatId)) {
            return alert("이미선택된 좌석입니다.")
        }
        
        setSelectedSeatList([...selectedSeatList, seatId]);
    }

    const createTicket = () => {

        if(selectedSeatList.length<peopleCount) {
            return alert("좌석을 인원수만큼 선택해주세요.")
        }

        const ticketInfo = {
            memberId : sessionStorage.getItem("memberId"),
            screenScheduleId : selectScheduleId,
            price : allPrice,
            ticketingSeatDTOList : selectedSeatList
        }
        console.log("예매 등록데이터::::::",ticketInfo);
        axios.post("http://localhost:8080//ticketing/create",ticketInfo);
        alert("예매되었습니다 !");
        navigete('/mycgv');
    }

    useEffect(()=> {
        getTheaterInfo();
    },[])

    return (
        <>
        <Header/>
        <div className={styles.container}>
            <div className={styles.ticket}>
                <div className={styles.step}>
                    <div className={styles.step1}>
                        {
                            seatSelect ? 
                                <>
                                    <div className={`${styles.section} ${styles.sectionSeat}`}>
                                        <div className={styles.head}>
                                            <h3 className={styles.sreader}>인원 / 좌석</h3>
                                        </div>
                                        <div className={styles.body}>
                                            <div className={styles.peopleBox}>
                                                <h2>인원</h2>
                                                <select onChange={(e)=> {
                                                    setPeopleCount(e.target.value);
                                                    setAllPrice(e.target.value*remainInfo.price);
                                                }}>
                                                    <option title="현재 선택됨" selected="" value="1">1명</option>
                                                    <option title="현재 선택됨" selected="" value="2">2명</option>
                                                    <option title="현재 선택됨" selected="" value="3">3명</option>
                                                    <option title="현재 선택됨" selected="" value="4">4명</option>
                                                </select>
                                                <span className={styles.seatReset} onClick={()=>setSelectedSeatList([])}>좌석 초기화</span>
                                            </div>
                                            <div className={styles.seatBox}>
                                                <div className={styles.seatWrap}>
                                                    <span>A: </span> 
                                                    <div className={styles.seatList}>
                                                        {
                                                            seatA &&
                                                                // seatA.map((seat)=> <li 
                                                                // className={(seat.isUsing) ? styles.disable:styles.active}
                                                                // onClick={()=> handleSelectedSeat(seat.id,seat.isUsing)}
                                                                // >
                                                                    
                                                                //     {seat.rowNumber}행 {seat.colNumber}열 좌석
                                                                // </li>)
                                                                seatA.map((seat) => <SeatItem key={seat.id} handleSelectedSeat={handleSelectedSeat} selectedSeatList={selectedSeatList} seatInfo={seat} />)
                                                        }
                                                    </div>
                                                </div>
                                                <div className={styles.seatWrap}>
                                                    <span>B: </span>
                                                    <div className={styles.seatList}>
                                                        {
                                                            seatB &&
                                                                seatB.map((seat)=> <SeatItem key={seat.id} handleSelectedSeat={handleSelectedSeat} selectedSeatList={selectedSeatList} seatInfo={seat} />)
                                                        }
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className={`${styles.section} ${styles.sectionBook}`}>
                                        <div className={styles.head}>
                                            <h3 className={styles.sreader}>최종 선택</h3>
                                        </div>
                                        <div className={styles.body}>
                                            <div className={styles.priceBox}>
                                                <h2>가격 : 
                                                    {
                                                        remainInfo && 
                                                            <span>{remainInfo.price*peopleCount}</span>
                                                    }
                                                </h2>
                                            </div>
                                            <div className={styles.lastBtn}>
                                                {
                                                    (sessionStorage.getItem("memberId")) ? <Button onClick={()=>createTicket()}>예매하기</Button>
                                                        : <Button onClick={()=>handleBeloginReserve()}>예매하기</Button>

                                                }
                                            </div>
                                        </div>
                                    </div>
                                </>
                                :
                                <>
                                    <div className={`${styles.section} ${styles.sectionMovie}`}>
                                        <div className={styles.head}>
                                            <h3 className={styles.sreader}>영화</h3>
                                        </div>
                                        <div className={styles.body}>
                                            <div className={styles.movieSelect}>
                                                <div className={styles.movieList}>
                                                    <ul className={styles.movieListBox}>
                                                        <li className={styles.movieItem}>{theaterInfo.title}</li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className={`${styles.section} ${styles.sectionTime}`}>
                                        <div className={styles.head}>
                                            <h3 className={styles.sreader}>시간</h3>
                                        </div>
                                        <div className={styles.body}>
                                            <div className={styles.timeOption}>
                                                <span class="morning"><BsFillSunriseFill/>조조</span>
                                                <span class="night"><BsFillMoonStarsFill/>심야</span>
                                            </div>
                                            <div className={styles.timeListBox}>
                                                
                                                {
                                                    readTheater &&

                                                        readTheater.map((item,idx)=> <TheaterInfos info={item} idx={idx} 
                                                            settingScheduleId={settingScheduleId} 
                                                            selectScheduleId={selectScheduleId}
                                                            setOnSeatBtn={setOnSeatBtn}
                                                            />)
                                                }
                                            
                                            </div>
                                        </div>
                                    </div>
                                    <div className={`${styles.section} ${styles.sectionBtn}`}>
                                        <div className={styles.head}>
                                            <h3 className={styles.sreader}>최종</h3>
                                        </div>
                                        <div className={styles.body}>
                                            <div className={styles.seatBtnBox}>
                                                {
                                                    onSeatBtn ? 
                                                        <Button className={styles.seatBtnOn} onClick={()=> handleSeatBtnClick()}>좌석선택</Button>
                                                        :
                                                        <Button className={styles.seatBtn} onClick={()=> alert("시간을 선택해주세요.")}>좌석선택</Button>
                                                }
                                            </div>
                                        </div>
                                    </div>
                                </>
                        }
                        
                    </div>
                </div>

            </div>
        </div>
        </>
    );
};

export default Reserve;
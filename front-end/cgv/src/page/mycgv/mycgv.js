import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Header from '../../component/homeComponent/header';
import UserInfo from '../../component/mycgvComponent/userInfo';
import styles from './mycgv.module.css';

const Mycgv = () => {

    const [memberInfo, setMemberInfo] = useState("");
    const [ticketList, setTicketList] = useState([]);

    const [ticketingCount, setTicketingCount] = useState("");

    const getTicketList = async () => {
        const memberId = sessionStorage.getItem("memberId");

        const res = await (await axios.get(`http://localhost:8080/user/${memberId}`)).data;

        console.log(res.data);
        setMemberInfo(res.data.memberInfoDTO);
        setTicketList(res.data.memberTicketingInfoDTOList);
        setTicketingCount(res.data.ticketingCount);
    }

    useEffect(()=> {
        getTicketList();
    },[])

    return (
        <>
            <Header/>
            <div className={styles.container}>
                <div className={styles.contents}>
                    <UserInfo memberInfo={memberInfo} ticketList={ticketList} ticketingCount={ticketingCount}/>
                </div> 
            </div>
        </>
    );
};

export default Mycgv;
import React from 'react';
import { Link } from 'react-router-dom';
import styles from './userInfo.module.css';
import { BsPlusSquare } from 'react-icons/bs';
import ReserveHistoryItem from './reserveHistoryItem';

const UserInfo = (props) => {

    const { memberInfo, ticketList, ticketingCount } = props;


    return (
        <div className={styles.container}>
            <div className={styles.infoBox}>
                <div className={styles.userInfo}>
                    <div className={styles.contentsBox}>
                        <div className={styles.nameBox}>
                            <strong>{sessionStorage.getItem("memberName")} 님</strong>
                        </div>
                    </div>

                    <div className={styles.userBasicInfo}>
                        <h2>아이디 : {memberInfo.memberId}</h2>
                        <h2>성별 : {memberInfo.gender}</h2>
                        <h2>나이 : {memberInfo.age}</h2>
                        <h2>전화번호 : {memberInfo.phone}</h2>
                    </div>
                </div>
            </div>

            <div className={styles.reserveBox}>
                <div className={styles.myReserveHead}>
                    <h3 style={{marginRight:"15px"}}>MY 예매내역</h3>
                    <p>
                        <em>'{ticketingCount}'건</em>
                        <Link to='/' className={styles.link}>
                            <BsPlusSquare/>
                        </Link>
                    </p>
                </div>
                <div className={styles.reservedListBox}>
                    {
                        ticketList && 
                            ticketList.map((ticket) => (
                                <ReserveHistoryItem key={ticket.ticketId} ticket={ticket}/>
                            ))
                    }
                    {
        (ticketList.length<1) &&
               <span>고객님의 최근 예매내역이 없습니다.</span>
}
                </div>
            </div>
        </div>
    );
};

export default UserInfo;
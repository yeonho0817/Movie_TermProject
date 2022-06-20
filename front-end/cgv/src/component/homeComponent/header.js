import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { isLogin } from '../../page/login/login';

import styles from './header.module.css';

const Header = () => {
    const navigate = useNavigate();
    const logout = () => {
        sessionStorage.removeItem("memberId");
        sessionStorage.removeItem("memberName");

        alert("로그아웃했습니다.");

        navigate("/");
    }
    console.log((sessionStorage.getItem("memberId")));

    return (
        <div className={styles.header}>
            <div className={styles.headerContent}>
                <div className={styles.contents}>
                    <h1>
                        <Link to="/" className={styles.link}>
                            <img src="https://img.cgv.co.kr/R2014/images/common/logo/logoRed.png" alt="CGV"/>
                        </Link>
                        <span>CULTUREPLEX</span>
                    </h1>
                    <ul className={styles.memberInfo}>
                        {
                            isLogin() ? 
                                <>
                                    <li onClick={() => {logout()}}>
                                        <Link to="/login" className={styles.link}>
                                            <img src="https://img.cgv.co.kr/R2014/images/common/ico/loginPassword.png" alt="로그아웃"/>
                                            <span>로그아웃</span>
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/mycgv" className={styles.link}>
                                            <img src="https://img.cgv.co.kr/R2014/images/common/ico/loginMember.png" alt="회원가입"/>
                                            <span>MY CGV</span>
                                        </Link>
                                    </li>
                                </>
                            :
                                <>
                                    <li>
                                        <Link to="/login" className={styles.link}>
                                            <img src="https://img.cgv.co.kr/R2014/images/common/ico/loginPassword.png" alt="로그인"/>
                                            <span>로그인</span>
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/join" className={styles.link}>
                                            <img src="https://img.cgv.co.kr/R2014/images/common/ico/loginJoin.png" alt="회원가입"/>
                                            <span>회원가입</span>
                                        </Link>
                                    </li>
                                </>
                        }
                    </ul>
                </div>
            </div>
            <nav className={styles.nav}>
                <div className={styles.contents}>
                    <ul className={styles.navMenu}>
                        <li>
                            <h2>
                                <Link to="/movies" className={styles.link}>영화</Link>
                            </h2>
                        </li>
                        <li>
                            <h2>
                                <Link to="/theater" className={styles.link}>극장</Link>
                            </h2>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    );
};

export default Header;
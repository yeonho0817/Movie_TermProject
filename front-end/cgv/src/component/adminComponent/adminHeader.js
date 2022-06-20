import React from 'react';
import styles from './adminHeader.module.css'

function AdminHeader(props) {
    return (
        <div className={styles.header}>
            <div className={styles.headerContent}>
                <div className={styles.contents}>
                    <h1>
                        {/* <Link to="/" className={styles.link}> */}
                            <img src="https://img.cgv.co.kr/R2014/images/common/logo/logoRed.png" alt="CGV"/>
                        {/* </Link> */}
                        <span style={{marginRight:"30px"}}>CULTUREPLEX</span>

                        <h2>관리자 페이지</h2>
                    </h1>

                </div>
            </div>
           
        </div>
    );
}

export default AdminHeader;
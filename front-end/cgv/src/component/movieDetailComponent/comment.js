import React, { useEffect, useState } from 'react';
import styles from './comment.module.css';
import { AiOutlineLike } from 'react-icons/ai';
import axios from 'axios';
import ModeEditOutlineTwoToneIcon from '@mui/icons-material/ModeEditOutlineTwoTone';
import DeleteTwoToneIcon from '@mui/icons-material/DeleteTwoTone';
import Modal from "react-modal";
import UpdateReview from './updateReview'

const Comment = (props) => {
    const {info, handleDeleteRating} = props;

    const [contents, setContents] = useState(info.contents);
    const [ratingPoint, setRatingPoint] = useState(info.ratingPoint);

    const [modalOpen, setModalOpen] = useState(false);

    const [likeNumber, setLikeNumber] = useState(info.likesNumber);

    const like = async () => {
        await axios.put(`http://localhost:8080/rating/like/${info.id}`);

        setLikeNumber(likeNumber+1)
    }

    

    useEffect(() => {
        setLikeNumber(info.likesNumber);
    }, [])

    return (
        <div className={styles.itemBox}>
            {

                Number(sessionStorage.getItem("memberId"))===Number(info.memberPK) ?

                <div className={styles.editBox}>
                    <ModeEditOutlineTwoToneIcon onClick={() => {setModalOpen(true)}}/>
                    <DeleteTwoToneIcon onClick={() => {handleDeleteRating(info.id);}}/>
                </div>

                :

                null
            }

            <div>
                아이디 : {info.memberId}
            </div>
            <div>
                내용 : {contents}
            </div>
            <div>
                평점 : {ratingPoint}
            </div>
            <div className={styles.bottom}>
                <span>등록일 : {info.registerDatetime} </span>
                <span className={styles.likeBtn} onClick={() => {like();}}><AiOutlineLike/> <span>{likeNumber}</span></span>
            </div>


            <Modal
                isOpen={modalOpen}
                ariaHideApp={false}
                shouldFocusAfterRender={true}
                onRequestClose={() => setModalOpen(false)}
                style={{
                overlay: {
                    position: "fixed",
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    backgroundColor: "rgba(126, 147, 149, 0.83)",
                },
                content: {
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    border: "1px solid #ccc",
                    background: "#fff",
                    overflow: "auto",
                    WebkitOverflowScrolling: "touch",
                    borderRadius: "4px",
                    outline: "none",
                    padding: "20px",
                    width: "600px",
                    height: "600px",
                
                },
                }}
            >
                <UpdateReview info={info} setContents={setContents} setRatingPoint={setRatingPoint} setModalOpen={setModalOpen}/>
                
            </Modal>
        </div>
    );
};

export default Comment;
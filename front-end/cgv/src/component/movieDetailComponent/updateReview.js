import { TextField } from '@mui/material';
import axios from 'axios';
import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import styled from "styled-components";
import styles from './updateReview.module.css';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';

const UpdateReview = (props) => {

    const {setModalOpen, info, setRatingPoint, setContents} = props;
    
    const [point, setPoint] = useState([]);
    const [content,setContent] = useState(props.contents);


    const handleChange = (event) => {
      setPoint(event.target.value);
    };

    const handleUpdate = async () => {

      const data = {
        ratingId:info.id,
        ratingPoint:point,
        contents:content
      }

      await axios.put("http://localhost:8080/rating/update", data).then((res) => {
        alert("정상적으로 수정되었습니다.");

        setRatingPoint(point);
        setContents(content);
      });
      setModalOpen(false);
    }



    return (
        <Wrap>
            <p className="text-raiting">평점</p>
            
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              // value={age}
              defaultValue={info.ratingPoint}
              label="Age"
              onChange={handleChange}
            >
              <MenuItem value={1}>1</MenuItem>
              <MenuItem value={2}>2</MenuItem>
              <MenuItem value={3}>3</MenuItem>
              <MenuItem value={4}>4</MenuItem>
              <MenuItem value={5}>5</MenuItem>
            </Select>
            <br/><br/><br/>
            <TextField
                id="outlined-basic"
                label="내용"
                variant="outlined"
                value={content}
                defaultValue={info.contents}
                name="postContents"
                onChange={(e)=> setContent(e.target.value)}
                multiline
                rows={10}
                maxRows={15}
              />
              <Button className={styles.btn} onClick={()=>handleUpdate()}>수정하기</Button>
              <Button onClick={() => setModalOpen(false)}>닫기</Button>
        </Wrap>
    );
};

export default UpdateReview;

const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  padding-top: 15px;
`;

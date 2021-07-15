import React from 'react';
import PreviewItem from '../PreviewItem/PreviewItem';
import { Container } from './PreviewList.styles';
// testing
import { reviewList } from '../../db.json';

const PreviewList = () => {
  const reviews = reviewList.slice(0, 4);

  return (
    <Container>
      {reviews.map((review) => (
        <li key={review.id}>
          <PreviewItem review={review} />
        </li>
      ))}
    </Container>
  );
};

export default PreviewList;

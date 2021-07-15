import React from 'react';
import PropTypes from 'prop-types';
import Label from '../Label/Label';
import { LABEL_SIZE_TYPE } from '../Label/Label.styles';
import {
  Container,
  TopContainer,
  Content,
  BottomContainer,
  ShotVerified,
} from './PreviewItem.styles';
import { VACCINE_COLOR, VACCINE, FONT_COLOR } from '../../constants';

const PreviewItem = ({ review }) => {
  const { writer, content, vaccinationType, createdAt } = review;
  const labelFontColor = vaccinationType === '아스트라제네카' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  return (
    <Container>
      <TopContainer>
        <Label
          sizeType={LABEL_SIZE_TYPE.SMALL}
          backgroundColor={VACCINE_COLOR[VACCINE[vaccinationType]]}
          fontColor={labelFontColor}
        >
          {vaccinationType}
        </Label>
        <ShotVerified>{writer?.shotVerified && '접종 확인'}</ShotVerified>
      </TopContainer>
      <Content>{content}</Content>
      <BottomContainer>{createdAt}</BottomContainer>
    </Container>
  );
};

PreviewItem.propTypes = {
  review: PropTypes.shape({
    writer: PropTypes.object.isRequired,
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
  }).isRequired,
};

PreviewItem.defaultProps = {};

export default PreviewItem;

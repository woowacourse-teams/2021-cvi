import React from 'react';
import PropTypes from 'prop-types';
import { AVATAR_SIZE_TYPE, Container } from './Avatar.styles';
import defaultImg from '../../assets/images/default_profile.png';

const Avatar = ({ sizeType, src }) => {
  return <Container sizeType={sizeType} src={src}></Container>;
};

Avatar.propTypes = {
  sizeType: PropTypes.string,
  src: PropTypes.string,
};

Avatar.defaultProps = {
  sizeType: AVATAR_SIZE_TYPE.SMALL,
  src: defaultImg,
};

export default Avatar;

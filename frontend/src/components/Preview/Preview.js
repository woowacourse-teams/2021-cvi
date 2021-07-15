import React from 'react';
import { FONT_COLOR, PATH } from '../../constants';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';
import Button from '../Button/Button';
import Frame from '../Frame/Frame';
import { Title, Container, ButtonContainer } from './Preview.styles';
import HomeIcon from '../../assets/icons/home.svg';
import PropTypes from 'prop-types';
import PreviewList from '../PreviewList/PreviewList';
import { useHistory } from 'react-router-dom';

const Preview = ({ title }) => {
  const history = useHistory();

  const goReviewPage = () => {
    history.push(`${PATH.REVIEW}`);
  };

  return (
    <div>
      {title && <Title>{title}</Title>}
      <Frame width="100%">
        <Container>
          <ButtonContainer>
            <Button
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              color={FONT_COLOR.PURPLE_GRAY}
              withIcon={true}
              onClick={goReviewPage}
            >
              <div>더보기</div>
              <HomeIcon width="18" height="18" stroke={FONT_COLOR.PURPLE_GRAY} />
            </Button>
          </ButtonContainer>
          <PreviewList />
        </Container>
      </Frame>
    </div>
  );
};

Preview.propTypes = {
  title: PropTypes.string,
};

Preview.defaultProps = {
  title: '',
};

export default Preview;
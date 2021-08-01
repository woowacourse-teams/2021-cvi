import styled from '@emotion/styled';

const LABEL_SIZE_TYPE = Object.freeze({
  SMALL: 'SMALL',
  MEDIUM: 'MEDIUM',
  LARGE: 'LARGE',
});

const labelStyle = {
  SMALL: {
    height: '2.4rem',
    fontSize: '1.2rem',
    borderRadius: '1.2rem',
    padding: '0 1rem',
  },
  MEDIUM: {
    height: '3.2rem',
    fontSize: '1.6rem',
    borderRadius: '1.6rem',
    padding: '0 1.2rem',
  },
  LARGE: {
    height: '4.0rem',
    fontSize: '2.0rem',
    borderRadius: '2.0rem',
    padding: '0 1.2rem',
  },
};

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  line-height: 1.5;
  width: fit-content;
  background-color: ${({ backgroundColor }) => backgroundColor};
  color: ${({ fontColor }) => fontColor};

  ${({ sizeType }) => labelStyle[sizeType] || labelStyle[LABEL_SIZE_TYPE.SMALL]};
`;

export { LABEL_SIZE_TYPE, Container };

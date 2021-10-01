import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { FONT_COLOR, PALETTE } from '../../../constants';

const LoadingContainer = styled.div`
  position: relative;
`;

const Table = styled.table`
  width: 100%;
  height: 100%;
`;

const Thead = styled.thead`
  text-align: left;
`;

const Tbody = styled.tbody`
  color: ${FONT_COLOR.BLACK};

  & > tr:nth-of-type(2n-1) {
    background-color: #f2f2f2;
  }
`;

const Tr = styled.tr`
  height: 4.18rem;
  color: ${FONT_COLOR.BLACK};

  & > th {
    font-weight: 400;
    font-size: 1.2rem;
    color: ${FONT_COLOR.LIGHT_GRAY};
  }

  & > td {
    font-size: 1.2rem;
    padding: 0.6rem;
    border-radius: 0.4rem;
    font-weight: 600;
  }
`;

const Span = styled.span`
  font-size: 1rem;
  color: ${FONT_COLOR.LIGHT_GRAY};
  font-weight: 400;
  margin-left: 0.2rem;
`;

const IncreaseIcon = styled.span`
  color: ${PALETTE.RED500};
`;

const frameStyle = css`
  height: 100%;
  padding: 2rem 1rem;
`;

export { LoadingContainer, Table, Thead, Tbody, Tr, Span, IncreaseIcon, frameStyle };

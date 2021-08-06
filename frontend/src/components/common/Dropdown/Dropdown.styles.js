import styled from '@emotion/styled';

const Container = styled.div`
  position: relative;
  min-width: 10rem;
`;

const SelectedFilter = styled.div``;

const MenuList = styled.div`
  position: absolute;
  width: max-content;
  border-radius: 1rem;
  padding: 0 1.2rem;
  background-color: #ffffff;
  box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.3);
  z-index: 100;
  right: 0;

  & li {
    height: 5rem;
    display: flex;
    align-items: center;
    padding: 0 0.8rem;
    width: 100%;

    & > * {
      width: 100%;
      font-size: 1.4rem;
      font-weight: 500;
      color: #333;
      transition: font-size 0.1s ease;
      text-align: left;
    }
  }

  & li:not(:last-child) {
    border-bottom: 1px solid #aaa;
  }

  display: ${({ isDropdownVisible }) => (isDropdownVisible ? 'block' : 'none')};
`;

export { Container, SelectedFilter, MenuList };

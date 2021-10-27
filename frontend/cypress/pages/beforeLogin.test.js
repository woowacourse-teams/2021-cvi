import { ALERT_MESSAGE, PAGING_SIZE } from '../../src/constants';

describe('Before Login Test', () => {
  const getTheLatestPreviewList = () => {
    cy.intercept(
      'GET',
      `https://dev.cvi-korea.r-e.kr/api/v1/posts/paging?offset=0&size=${PAGING_SIZE}&sort=CREATED_AT_DESC`,
      {
        fixture: 'theLatestPreviewList',
      },
    ).as('theLatestPreviewList');
  };

  const getTheMostFamousPreviewList = () => {
    cy.intercept(
      'GET',
      `https://dev.cvi-korea.r-e.kr/api/v1/posts/paging?offset=0&size=${PAGING_SIZE}&sort=LIKE_COUNT_DESC`,
      {
        fixture: 'theMostFamousPreviewList',
      },
    ).as('theMostFamousPreviewList');
  };

  before(() => {
    getTheLatestPreviewList();
    getTheMostFamousPreviewList();

    cy.visit('');
    cy.wait('@theLatestPreviewList');
    cy.wait('@theMostFamousPreviewList');
    cy.waitForReact();
  });

  // 홈페이지 컴포넌트 확인
  it('홈페이지에 접속하여 접종 현황을 본다.', () => {
    cy.react('VaccinationState', { props: { title: '접종 현황' } }).should('exist');
  });

  it('홈페이지에 접속하여 최신 글을 본다.', () => {
    cy.react('Preview', { props: { title: '최신 글' } }).should('exist');
  });

  it('홈페이지에 접속하여 실시간 인기 글을 본다.', () => {
    cy.react('Preview', { props: { title: '실시간 인기 글' } }).should('exist');
  });

  it('홈페이지에 접속하여 접종 예약 하러가기 버튼을 본다', () => {
    cy.contains('a', '접종 예약 하러가기').should('exist');
  });

  it('홈페이지에 접속하여 접종 후기 쓰러가기 버튼을 본다', () => {
    cy.react('Button').contains('접종 후기 쓰러가기').should('exist');
  });

  it('홈페이지에 접속하여 잔여 백신 보러가기 버튼을 본다', () => {
    cy.contains('a', '잔여 백신 보러가기').should('exist');
  });

  // + 새로운 창 열리는 것 확인하는 테스트 코드

  // warning 발생
  // stub 필요
  it.skip('홈페이지에 있는 최신 글의 첫 번째 후기를 클릭하면, 최신 글 첫 번째 후기의 상세 페이지가 보인다.', () => {
    cy.react('Preview', { props: { title: '최신 글' } })
      .find('ul li:first')
      .click();
    cy.location().should((loc) => {
      expect(loc.pathname).contains('/review/');
    });

    cy.contains('a', '홈').click();
  });

  it('홈페이지에 있는 실시간 인기 글의 첫 번째 후기를 클릭하면, 실시간 인기 글 첫 번째 후기의 상세 페이지가 보인다.', () => {
    cy.react('Preview', { props: { title: '실시간 인기 글' } })
      .find('ul li:first')
      .click();

    cy.location().should((loc) => {
      expect(loc.pathname).contains('/review/');
    });
  });

  it('목록 보기 버튼을 클릭하면, 접종 후기 페이지가 보인다.', () => {
    cy.contains('div', '목록 보기').click();

    cy.location('pathname', { timeout: 4000 }).should('equal', '/review');
  });

  // 클릭했는데, 렌더링이 안되는 경우도 있는 것 같기도 하고..
  it('모더나 탭을 클릭하면, 모더나 접종 후기만 보인다.', () => {
    cy.react('Button').contains('모더나').click();
    // cy.contains('button', '모더나').click();

    // cy.waitForReact();
    cy.react('Label').contains('화이자').should('not.exist');
    cy.react('Label').contains('얀센').should('not.exist');
    cy.react('Label').contains('아스트라제네카').should('not.exist');
  });

  it.skip('화이자 탭을 클릭하면, 화이자 접종 후기만 보인다.', () => {
    cy.contains('button', '화이자').click();

    cy.react('Label').contains('모더나').should('not.exist');
    cy.react('Label').contains('얀센').should('not.exist');
    cy.react('Label').contains('아스트라제네카').should('not.exist');
  });

  it.skip('얀센 탭을 클릭하면, 얀센 접종 후기만 보인다.', () => {
    cy.contains('button', '얀센').click();

    cy.react('Label').contains('화이자').should('not.exist');
    cy.react('Label').contains('모더나').should('not.exist');
    cy.react('Label').contains('아스트라제네카').should('not.exist');
  });

  it.skip('아스트라제네카 탭을 클릭하면, 아스트라제네카 접종 후기만 보인다.', () => {
    cy.contains('button', '아스트라제네카').click();

    cy.react('Label').contains('화이자').should('not.exist');
    cy.react('Label').contains('모더나').should('not.exist');
    cy.react('Label').contains('얀센').should('not.exist');
    expect();
  });

  it.skip('후기 작성 버튼을 누르면 로그인을 안내하는 alert가 보이고, 취소 버튼을 클릭한다.', () => {
    cy.react('Button').contains('후기 작성').click();

    cy.on('window:confirm', (text) => {
      expect(text).to.equal(ALERT_MESSAGE.NEED_LOGIN);
    });
    cy.on('window:confirm', () => false);
  });

  it.skip('접종 후기 페이지가 보인다', () => {
    cy.location('pathname').should('equal', '/review');
  });

  it.skip('후기 작성 버튼을 누르고 확인을 누르면, 로그인 페이지가 보인다', () => {
    cy.contains('button', '후기 작성').click();

    cy.on('window:confirm', () => true);
  });

  it.skip('로그인 페이지가 보인다.', () => {
    cy.location('pathname').should('equal', '/login');
  });
});

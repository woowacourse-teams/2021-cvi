import { ALERT_MESSAGE } from '../../src/constants';

const BASE_URL = 'https://dev.cvi-korea.r-e.kr/api/v1';

describe('Before Login Test', () => {
  const getPublicVaccinations = () => {
    cy.intercept('GET', `${BASE_URL}/publicdata/vaccinations`, {
      fixture: 'publicVaccinations',
    }).as('getPublicVaccinations');
  };

  const getPublicVaccinationsWorld = () => {
    cy.intercept('GET', `${BASE_URL}/publicdata/vaccinations/world`, {
      fixture: 'publicVaccinationsWorld',
    }).as('getPublicVaccinationsWorld');
  };

  const getTheLatestPreviewList = () => {
    cy.intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=CREATED_AT_DESC`, {
      fixture: 'theLatestPreviewList',
    }).as('getTheLatestPreviewList');
  };

  const getTheMostFamousPreviewList = () => {
    cy.intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=LIKE_COUNT_DESC`, {
      fixture: 'theMostFamousPreviewList',
    }).as('getTheMostFamousPreviewList');
  };

  const getCommentList = (id) =>
    cy
      .intercept('GET', `${BASE_URL}/posts/${id}/comments/paging?offset=0&size=10`, {
        fixture: 'commentList',
      })
      .as('getCommentList');

  before(() => {
    getPublicVaccinations();
    getPublicVaccinationsWorld();
    getTheLatestPreviewList();
    getTheMostFamousPreviewList();

    cy.visit('');
    cy.wait('@getPublicVaccinations');
    cy.wait('@getPublicVaccinationsWorld');
    cy.wait('@getTheLatestPreviewList');
    cy.wait('@getTheMostFamousPreviewList');
    cy.waitForReact();
  });

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

  it('홈페이지에 있는 최신 글의 첫 번째 후기를 클릭하면, 최신 글 첫 번째 후기의 상세 페이지가 보인다.', () => {
    const reviewId = 10;

    getPublicVaccinations();
    getPublicVaccinationsWorld();
    getTheLatestPreviewList();
    getTheMostFamousPreviewList();
    cy.intercept('GET', `${BASE_URL}/posts/${reviewId}`, { fixture: 'theLatestPreview' }).as(
      'getTheLatestPreview',
    );
    getCommentList(reviewId);

    cy.react('Preview', { props: { title: '최신 글' } })
      .find('ul li:first')
      .click();

    cy.wait('@getTheLatestPreview');
    cy.wait('@getCommentList');
    cy.url().should('include', `review/${reviewId}`);

    cy.contains('a', '홈').click();

    cy.wait('@getTheLatestPreviewList');
    cy.wait('@getTheMostFamousPreviewList');
  });

  it('홈페이지에 있는 실시간 인기 글의 첫 번째 후기를 클릭하면, 실시간 인기 글 첫 번째 후기의 상세 페이지가 보인다.', () => {
    const reviewId = 14;

    cy.intercept('GET', `${BASE_URL}/posts/${reviewId}`, { fixture: 'theMostFamousPreview' }).as(
      'getTheMostFamousPreview',
    );
    getCommentList(reviewId);

    cy.react('Preview', { props: { title: '실시간 인기 글' } })
      .find('ul li:first')
      .click();

    cy.url().should('include', `review/${reviewId}`);
    cy.wait('@getTheMostFamousPreview');
    cy.wait('@getCommentList');
  });

  it('목록 보기 버튼을 클릭하면, 접종 후기 페이지가 보인다.', () => {
    cy.intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=CREATED_AT_DESC`, {
      fixture: 'updatedReviewList',
    }).as('getReviewList');

    cy.contains('div', '목록 보기').click();

    cy.wait('@getReviewList');
    cy.location('pathname').should('equal', '/review');
  });

  it('모더나 탭을 클릭하면, 모더나 접종 후기만 보인다.', () => {
    cy.intercept(
      'GET',
      `${BASE_URL}/posts/paging?vaccinationType=MODERNA&offset=0&size=10&sort=CREATED_AT_DESC`,
      { fixture: 'modernaReviewList' },
    ).as('getModernaReviewList');

    cy.react('Button').contains('모더나').click();

    cy.wait('@getModernaReviewList');
    cy.react('Label').contains('화이자').should('not.exist');
    cy.react('Label').contains('얀센').should('not.exist');
    cy.react('Label').contains('아스트라제네카').should('not.exist');
  });

  it('화이자 탭을 클릭하면, 화이자 접종 후기만 보인다.', () => {
    cy.intercept(
      'GET',
      `${BASE_URL}/posts/paging?vaccinationType=PFIZER&offset=0&size=10&sort=CREATED_AT_DESC`,
      { fixture: 'pfizerReviewList' },
    ).as('getPfizerReviewList');

    cy.react('Button').contains('화이자').click();

    cy.wait('@getPfizerReviewList');
    cy.react('Label').contains('모더나').should('not.exist');
    cy.react('Label').contains('얀센').should('not.exist');
    cy.react('Label').contains('아스트라제네카').should('not.exist');
  });

  it('얀센 탭을 클릭하면, 얀센 접종 후기만 보인다.', () => {
    cy.intercept(
      'GET',
      `${BASE_URL}/posts/paging?vaccinationType=JANSSEN&offset=0&size=10&sort=CREATED_AT_DESC`,
      { fixture: 'janssenReviewList' },
    ).as('getJanssenReviewList');

    cy.react('Button').contains('얀센').click();

    cy.wait('@getJanssenReviewList');
    cy.react('Label').contains('화이자').should('not.exist');
    cy.react('Label').contains('모더나').should('not.exist');
    cy.react('Label').contains('아스트라제네카').should('not.exist');
  });

  it('아스트라제네카 탭을 클릭하면, 아스트라제네카 접종 후기만 보인다.', () => {
    cy.intercept(
      'GET',
      `${BASE_URL}/posts/paging?vaccinationType=ASTRAZENECA&offset=0&size=10&sort=CREATED_AT_DESC`,
      { fixture: 'astrazenecaReviewList' },
    ).as('getAstrazenecaReviewList');

    cy.react('Button').contains('아스트라제네카').click();

    cy.wait('@getAstrazenecaReviewList');
    cy.react('Label').contains('화이자').should('not.exist');
    cy.react('Label').contains('모더나').should('not.exist');
    cy.react('Label').contains('얀센').should('not.exist');
    expect();
  });

  it('후기 작성 버튼을 누르면 로그인을 안내하는 alert가 보이고, 취소 버튼을 클릭한다.', () => {
    cy.react('Button').contains('후기 작성').click();

    cy.on('window:confirm', (text) => {
      expect(text).to.equal(ALERT_MESSAGE.NEED_LOGIN);
    });
    cy.on('window:confirm', () => false);
  });

  it('접종 후기 페이지가 보인다', () => {
    cy.location('pathname').should('equal', '/review');
  });

  it('후기 작성 버튼을 누르고 확인을 누르면, 로그인 페이지가 보인다', () => {
    cy.react('Button').contains('후기 작성').click();

    cy.on('window:confirm', () => true);
  });

  it('로그인 페이지가 보인다.', () => {
    cy.location('pathname').should('equal', '/login');
  });
});

const BASE_URL = 'https://dev.cvi-korea.r-e.kr/api/v1';

describe('After Login Test', () => {
  before(() => {
    cy.visit('http://localhost:9000');

    cy.waitForReact();
  });

  const login = () => {
    cy.window()
      .its('store')
      .invoke('dispatch', {
        type: 'auth/myInfo/fulfilled',
        payload: {
          accessToken: 'accessToken',
          user: {
            id: 8,
            nickname: 'nickname',
            ageRange: { meaning: '20대', minAge: 20, maxAge: 30 },
            socialProvider: 'KAKAO',
            socialId: 'socialId',
            socialProfileUrl:
              'http://k.kakaocdn.net/dn/xGvcl/btranGk6QWP/uvRGXQeokXgIhPRD0wTPgk/img_640x640.jpg',
          },
        },
      });
  };

  it('로그인 후 홈화면으로 온다.', () => {
    login();
  });

  it('접종 후기 메뉴를 클릭하면, 접종 후기 페이지가 보인다.', () => {
    login();

    cy.contains('a', '접종후기').click();
    cy.url().should('include', '/review');
  });

  it('후기 작성 버튼을 클릭해서, 후기를 작성한다.', () => {
    cy.intercept('POST', `${BASE_URL}/posts`, {
      state: 'SUCCESS',
    }).as('createReview');
    cy.intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=CREATED_AT_DESC`, {
      fixture: 'newReviewList',
    }).as('getUpdatedReviewList');

    login();

    cy.react('Button').contains('후기 작성').click();

    cy.get('textarea').eq(0).type('모더나를 맞았어요. 별로 안 아프더라구요.');
    cy.react('Button').contains('제출하기').click();
    cy.wait('@createReview');
    cy.wait('@getUpdatedReviewList');
  });

  it('접종 후기에 페이지에서 내가 쓴 후기에 들어가서 수정한다.', () => {
    cy.intercept('GET', `${BASE_URL}/posts/8`, { fixture: 'newReview' }).as('getNewReview');
    cy.intercept('PUT', `${BASE_URL}/posts/8`, { state: 'SUCCESS' }).as('editReview');

    cy.react('ReviewItem').eq(0).click();
    cy.url().should('include', '/review/8');
    cy.wait('@getNewReview');

    cy.contains('button', '수정').click();
    cy.wait('@getNewReview');
    cy.url().should('include', '/review/8/edit');

    cy.get('textarea').type('다음날 되니 좀 아픈것 같아요 ㅠ ㅠ');
    cy.react('Button').contains('수정하기').click();
    cy.wait('@editReview');
  });

  it.skip('내가 쓴 후기를 삭제한다.', () => {
    cy.intercept('GET', `${BASE_URL}/posts/8`, { fixture: 'editReview' }).as('getUpdatedReview');
    cy.intercept('DELETE', `${BASE_URL}/posts/8`, { state: 'SUCCESS' }).as('deleteReview');

    cy.waitForReact();
    cy.wait('@getUpdatedReview');

    cy.react('Button').contains('삭제').eq(0).click();
    cy.wait('@deleteReview');
  });

  it.skip('내가 쓴 후기를 삭제한다2.', () => {
    cy.intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=CREATED_AT_DESC`, {
      fixture: 'reviewList',
    }).as('requestReviewList');

    cy.url().should('include', '/review');
    cy.wait('@requestReviewList');

    cy.react('ReviewItem').its('length').should('eq', 7);
  });
});

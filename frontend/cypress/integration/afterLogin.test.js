import { CONFIRM_MESSAGE } from '../../src/constants';

const BASE_URL = 'https://dev.cvi-korea.r-e.kr/api/v1';

describe('After Login Test', () => {
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

  const getReviewList = () =>
    cy
      .intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=CREATED_AT_DESC`, {
        fixture: 'reviewList',
      })
      .as('getReviewList');

  const getCommentList = () =>
    cy
      .intercept('GET', `${BASE_URL}/posts/8/comments/paging?offset=0&size=10`, {
        fixture: 'reviewList',
      })
      .as('getCommentList');

  before(() => {
    getReviewList();

    cy.visit('/review');
    cy.wait('@getReviewList');
    cy.waitForReact();
  });

  it('로그인 후 접종 후기 화면으로 간다.', () => {
    login();
  });

  it('후기 작성 버튼을 클릭해서, 후기를 작성한다.', () => {
    cy.intercept('POST', `${BASE_URL}/posts`, {
      state: 'SUCCESS',
    }).as('createReview');
    cy.intercept('GET', `${BASE_URL}/posts/paging?offset=0&size=10&sort=CREATED_AT_DESC`, {
      fixture: 'updatedReviewList',
    }).as('getUpdatedReviewList');

    cy.react('Button').contains('후기 작성').click();

    cy.get('textarea').eq(0).type('모더나를 맞았어요. 별로 안 아프더라구요.');
    cy.react('Button').contains('제출하기').click();
    cy.wait('@createReview');
    cy.wait('@getUpdatedReviewList');
  });

  it('내가 쓴 후기에 들어가서 수정한다.', () => {
    cy.intercept('PUT', `${BASE_URL}/posts/8`, { state: 'SUCCESS' }).as('editReview');
    cy.intercept('GET', `${BASE_URL}/posts/8`, { fixture: 'newReview' }).as('getNewReview');
    getCommentList();

    cy.react('ReviewItem').eq(0).click();
    cy.url().should('include', '/review/8');
    cy.wait('@getNewReview');
    cy.wait('@getCommentList');

    cy.contains('button', '수정').click();
    cy.wait('@getNewReview');
    cy.url().should('include', '/review/8/edit');

    cy.get('textarea').type('다음날 되니 좀 아픈것 같아요 ㅠ ㅠ');
    cy.react('Button').contains('수정하기').click();
    cy.wait('@editReview');
  });

  it('내가 쓴 후기를 삭제한다.', () => {
    cy.intercept('DELETE', `${BASE_URL}/posts/8`, { state: 'SUCCESS' }).as('deleteReview');
    cy.intercept('GET', `${BASE_URL}/posts/8`, { fixture: 'editedReview' }).as('getUpdatedReview');
    getReviewList();
    getCommentList();

    cy.visit('/review/8');
    cy.wait('@getUpdatedReview');
    cy.wait('@getCommentList');
    login();
    cy.waitForReact();

    cy.contains('button', '삭제').eq(0).click();
    cy.on('window:confirm', (text) => {
      expect(text).to.equal(CONFIRM_MESSAGE.DELETE_REVIEW);
    });
    cy.on('window:confirm', () => true);

    cy.wait('@deleteReview');

    cy.url().should('include', '/review');
    cy.wait('@getReviewList');

    cy.react('ReviewItem').its('length').should('eq', 7);
  });
});

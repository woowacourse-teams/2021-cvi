import { PAGING_SIZE } from '../../src/constants';

const BASE_URL = 'https://dev.cvi-korea.kro.kr/api/v1';

describe('After Login Test', () => {
  const getCreatedAtReviewList = () => {
    cy.intercept(
      'GET',
      `${BASE_URL}/paging?offset=0&size=${PAGING_SIZE}&sort=CREATED_AT_DESC`,
      (req) => {
        req.headers['Content-Type'] = 'application/json; charset=UTF-8';
        req.headers['Authorization'] = 'Bearer ';
        req.reply({
          fixture: 'reviewList',
        });
      },
    ).as('requestCreatedAtReviewData');
  };

  before(() => {
    // const asd = getCreatedAtReviewList();
    // console.log(asd);
    // getCreatedAtReviewList();
    cy.visit('');
    // login();
    // cy.setLocalStorage('accessToken', JSON.stringify('accessToken'));
    // cy.wait('@requestCreatedAtReviewData', { resquestTimeout: 10000 });
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
            shotVerified: false,
            socialProvider: 'KAKAO',
            socialId: 'socialId',
            socialProfileUrl:
              'http://k.kakaocdn.net/dn/xGvcl/btranGk6QWP/uvRGXQeokXgIhPRD0wTPgk/img_640x640.jpg',
          },
        },
      });
  };

  it.skip('로그인 후 홈화면으로 온다.', () => {
    login();
    // cy.waitForReact();

    // cy.visit('');
    // getCreatedAtReviewList();

    // cy.wait('@requestCreatedAtReviewData');

    // cy.window()
    //   .its('store')
    //   .invoke('getState')
    //   .should('deep.equal', {
    //     authReducer: {
    //       accessToken: 'accessToken',
    //       user: {
    //         id: 8,
    //         nickname: 'nickname',
    //         ageRange: { meaning: '20대', minAge: 20, maxAge: 30 },
    //         shotVerified: false,
    //         socialProvider: 'KAKAO',
    //         socialId: 'socialId',
    //         socialProfileUrl:
    //           'http://k.kakaocdn.net/dn/xGvcl/btranGk6QWP/uvRGXQeokXgIhPRD0wTPgk/img_640x640.jpg',
    //       },
    //     },
    //   });
    cy.setLocalStorage('accessToken', JSON.stringify('accessToken'));
  });

  it.skip('접종 후기 메뉴를 클릭하면, 접종 후기 페이지가 보인다.', () => {
    login();
    cy.waitForReact();
    cy.contains('a', '접종후기').click();

    cy.url().should('include', '/review');
  });

  const createReview = () => {
    cy.intercept('POST', `${BASE_URL}/posts`, {
      state: 'SUCCESS',
    }).as('requestCreateReview');
  };

  const editReview = () => {
    cy.intercept('POST', `${BASE_URL}/posts/7`, { state: 'SUCCESS' }).as('requestEditReview');
  };

  const getNewReviewList = () => {
    cy.intercept(
      'GET',
      `${BASE_URL}/paging?offset=0&size=${PAGING_SIZE}&sort=CREATED_AT_DESC`,
      (req) => {
        req.headers['Content-Type'] = 'application/json; charset=UTF-8';
        req.headers['Authorization'] = 'Bearer ';
        req.reply({
          fixture: 'newReviewList',
        });
      },
    ).as('requestNewReviewData');
  };

  const getNewReview = () => {
    cy.intercept('GET', `${BASE_URL}/posts/7`, { fixture: 'newReview' }).as('requestGetNewReview');
  };

  const getEditedReview = () => {
    cy.intercept('GET', `${BASE_URL}/posts/7`, { fixture: 'editReview' }).as(
      'requestGetEditReview',
    );
  };

  it.skip('후기 작성 버튼을 클릭해서, 후기를 작성한다.', () => {
    login();

    getCreatedAtReviewList();

    cy.waitForReact();

    cy.contains('button', '후기 작성').click();

    cy.on('window:confirm', () => false);
    createReview();
    cy.get('textarea').eq(0).type('모더나를 맞았어요. 별로 안 아프더라구요.');
    cy.contains('button', '제출하기').click();

    login();
    getNewReviewList();
    cy.visit('/review');
    cy.wait('@requestNewReviewData');
  });

  it.skip('접종 후기에 페이지에서 내가 쓴 후기에 들어가서 수정한다.', () => {
    cy.waitForReact();

    getNewReview();
    editReview();

    cy.react('ReviewItem').eq(0).click();
    cy.url().should('include', '/review/7');

    cy.contains('button', '수정').click();
    cy.url().should('include', '/review/7/edit');

    cy.get('textarea').type('다음날 되니 좀 아픈것 같아요 ㅠ ㅠ');
    cy.contains('button', '수정하기').click();

    getEditedReview();
    cy.visit('/review/7');
    login();
  });

  it.skip('내가 쓴 후기를 삭제한다.', () => {
    cy.waitForReact();

    cy.contains('button', '삭제').click();
    getCreatedAtReviewList();

    cy.visit('/review');
    login();
    cy.wait('@requestReviewData');
  });
});

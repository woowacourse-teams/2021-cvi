import { BASE_URL } from '../../src/constants';

describe('After Login Test', () => {
  const getReviewList = () => {
    cy.intercept('GET', `${BASE_URL}/posts`, { fixture: 'reviewList' }).as('requestReviewData');
  };

  before(() => {
    getReviewList();

    cy.visit('');
    cy.wait('@requestReviewData');
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
            socialProfileUrl:
              'http://k.kakaocdn.net/dn/xGvcl/btranGk6QWP/uvRGXQeokXgIhPRD0wTPgk/img_640x640.jpg',
          },
        },
      });
  };

  it('카카오로 시작하기 버튼을 누른다.', () => {
    cy.waitForReact();
    cy.visit('/login');

    getReviewList();

    cy.visit('');
    login();
    cy.wait('@requestReviewData');

    cy.window()
      .its('store')
      .invoke('getState')
      .should('deep.equal', {
        authReducer: {
          accessToken: 'accessToken',
          user: {
            id: 8,
            nickname: 'nickname',
            ageRange: { meaning: '20대', minAge: 20, maxAge: 30 },
            shotVerified: false,
            socialProfileUrl:
              'http://k.kakaocdn.net/dn/xGvcl/btranGk6QWP/uvRGXQeokXgIhPRD0wTPgk/img_640x640.jpg',
          },
        },
      });
    cy.setLocalStorage('accessToken', JSON.stringify('accessToken'));
  });
  // it('회원가입 페이지가 보인다.', () => {
  //   cy.visit('/signup');
  // });
  // it('회원 가입을 진행한다.', () => {
  //   cy.waitForReact();

  // cy.react('Selection').
  // cy.react('Input').type('test');
  // cy.react('Button').eq(0).click();

  // signup();
  // cy.visit('');
  // cy.wait('@requestSignup');

  // getReviewList();
  // cy.visit('');
  // cy.wait('@requestReviewData');
  // });

  // it('회원가입을 실패했다는 alert가 보인다.', () => {});
  // it('회원 가입을 다시 진행한다.', () => {});
  // it('회원가입을 성공했다는 alert가 보이고, 확인 버튼을 클릭하면 홈페이지가 보인다.', () => {});
  it('접종 후기 메뉴를 클릭하면, 접종 후기 페이지가 보인다.', () => {
    cy.waitForReact();
    cy.react('NavLink').eq(1).click();

    cy.url().should('include', '/review');
  });

  const createReview = () => {
    cy.intercept('POST', `${BASE_URL}/posts`, { state: 'SUCCESS' }).as('requestCreateReview');
  };

  const editReview = () => {
    cy.intercept('POST', `${BASE_URL}/posts/7`, { state: 'SUCCESS' }).as('requestEditReview');
  };

  const getNewReviewList = () => {
    cy.intercept('GET', `${BASE_URL}/posts`, { fixture: 'newReviewList' }).as(
      'requestNewReviewData',
    );
  };

  const getNewReview = () => {
    cy.intercept('GET', `${BASE_URL}/posts/7`, { fixture: 'newReview' }).as('requestGetNewReview');
  };

  const getEditedReview = () => {
    cy.intercept('GET', `${BASE_URL}/posts/7`, { fixture: 'editReview' }).as(
      'requestGetEditReview',
    );
  };

  it('후기 작성 버튼을 클릭해서, 후기를 작성한다.', () => {
    getReviewList();

    cy.waitForReact();

    cy.react('Button').eq(0).click();

    createReview();
    cy.get('textarea').eq(0).type('모더나를 맞았어요. 별로 안 아프더라구요.');
    cy.get('Button').eq(11).click();

    getNewReviewList();
    cy.visit('/review');
    login();
    cy.wait('@requestNewReviewData');
  });

  it('접종 후기에 페이지에서 내가 쓴 후기에 들어가서 수정한다.', () => {
    cy.waitForReact();

    getNewReview();
    editReview();

    cy.react('ReviewItem').eq(0).click();
    cy.url().should('include', '/review/7');

    cy.react('Button').eq(1).click();
    cy.url().should('include', '/review/7/edit');

    cy.get('textarea').type('다음날 되니 좀 아픈것 같아요 ㅠ ㅠ');
    cy.react('Button').eq(0).click();

    getEditedReview();
    cy.visit('/review/7');
    login();
  });

  it('내가 쓴 후기를 삭제한다.', () => {
    cy.waitForReact();

    cy.react('Button').eq(2).click();
    getReviewList();

    cy.visit('/review');
    login();
    cy.wait('@requestReviewData');
  });
});

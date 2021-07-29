import { BASE_URL } from '../../src/constants';

describe('E2E Test', () => {
  const getReviewList = () => {
    cy.intercept('GET', `${BASE_URL}/posts`, { fixture: 'reviewList' }).as('requestReviewData');
  };

  before(() => {
    getReviewList();

    cy.visit('');
    cy.wait('@requestReviewData');
  });

  it('홈페이지에 접속하여 접종 현황을 본다.', () => {
    cy.waitForReact();
    cy.react('VaccinationState', { props: { title: '접종 현황' }, exact: true }).contains(
      '접종 현황',
    );
  });

  it('홈페이지에 접속하여 접종 후기를 본다.', () => {
    getReviewList();

    cy.visit('');
    cy.wait('@requestReviewData');
  });

  it('홈페이지에 있는 첫 번째 접종 후기를 클릭하면, 첫 번째 접종 후기의 접종 상세 페이지가 보인다.', () => {
    const writerInfo = cy.get('ul li:first').children();
    console.log(writerInfo);
    cy.get('ul li:first').click();

    cy.location().should((loc) => {
      expect(loc.pathname).contains('/review/');
    });
  });

  it('목록 보기 버튼을 클릭하면, 전체 접종 후기 페이지가 보인다.', () => {
    cy.contains('div', '목록 보기').click();

    cy.location().should((loc) => {
      expect(loc.pathname).contains('/review');
    });
  });

  it('모더나 탭을 클릭하면, 모더나 접종 후기만 보인다.', () => {
    cy.contains('button', '모더나').click();

    cy.waitForReact();
    cy.react('Label').contains('화이자').should('have.length', '0');
    cy.react('Label').contains('얀센').should('have.length', '0');
    cy.react('Label').contains('아스트라제네카').should('have.length', '0');
  });

  it('화이자 탭을 클릭하면, 화이자 접종 후기만 보인다.', () => {
    cy.contains('button', '화이자').click();

    cy.waitForReact();
    cy.react('Label').contains('모더나').should('have.length', '0');
    cy.react('Label').contains('얀센').should('have.length', '0');
    cy.react('Label').contains('아스트라제네카').should('have.length', '0');
  });

  it('얀센 탭을 클릭하면, 얀센 접종 후기만 보인다.', () => {
    cy.contains('button', '얀센').click();

    cy.waitForReact();
    cy.react('Label').contains('화이자').should('have.length', '0');
    cy.react('Label').contains('모더나').should('have.length', '0');
    cy.react('Label').contains('아스트라제네카').should('have.length', '0');
  });

  it('아스트라제네카 탭을 클릭하면, 아스트라제네카 접종 후기만 보인다.', () => {
    cy.contains('button', '아스트라제네카').click();

    cy.waitForReact();
    cy.react('Label').contains('화이자').should('have.length', '0');
    cy.react('Label').contains('모더나').should('have.length', '0');
    cy.react('Label').contains('얀센').should('have.length', '0');
    expect();
  });

  it('후기 작성 버튼을 클릭하면, 로그인을 안내하는 alert가 보인다.', () => {
    cy.contains('button', '후기 작성').click();

    cy.on('window:confirm', (text) => {
      expect(text).to.equal('로그인이 필요합니다.');
    });
    cy.on('window:confirm', () => false);
  });

  it('취소 버튼을 클릭한다.', () => {
    cy.contains('button', '후기 작성').click();

    cy.on('window:confirm', () => false);
  });

  it('다시 후기 작성 버튼을 누르고, alert에서 확인 버튼을 클릭한다.', () => {
    cy.contains('button', '후기 작성').click();

    cy.on('window:confirm', () => true);
  });

  it('로그인 페이지가 보인다.', () => {
    cy.contains('div', '로그인');
    cy.contains('a', '네이버로 시작하기');
    cy.contains('a', '카카오로 시작하기');
  });

  it('카카오로 시작하기 버튼을 누른다.', () => {});
  it('회원가입 페이지가 보인다.', () => {});
  it('회원 가입을 진행한다.', () => {});
  it('회원가입을 실패했다는 alert가 보인다.', () => {});
  it('회원 가입을 다시 진행한다.', () => {});
  it('회원가입을 성공했다는 alert가 보이고, 확인 버튼을 클릭하면 홈페이지가 보인다.', () => {});
  it('접종 후기 메뉴를 클릭하면, 접종 후기 페이지가 보인다.', () => {});
  it('후기 작성 버튼을 클릭해서, 후기를 작성한다.', () => {});
  it('접종 후기에 페이지에서 내가 쓴 글에 들어가서 후기를 수정한다.', () => {});
  it('내가 쓴 글을 삭제한다.', () => {});
});

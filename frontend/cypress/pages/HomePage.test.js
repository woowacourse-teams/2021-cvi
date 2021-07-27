import { BASE_URL } from '../../src/constants';

describe('HomePage Test', () => {
  const getReviewList = () => {
    cy.intercept('GET', `${BASE_URL}/posts`, { fixture: 'reviewList' }).as('requestReviewData');
  };

  before(() => {
    cy.visit('');
    cy.waitForReact();
  });

  it('사용자는 HomePage에 접속하여 접종 현황을 볼 수 있다', () => {
    expect(cy.react('VaccinationState')).to.exist;
  });

  it('사용자는 HomePage에 접속하여 접종 후기를 볼 수 있다', () => {
    getReviewList();

    cy.visit('/');
    cy.wait('@requestReviewData');
  });
});

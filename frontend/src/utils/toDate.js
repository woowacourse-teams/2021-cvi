import { TO_DATE_TYPE } from '../constants';

const toDate = (type, string) => {
  if (type === TO_DATE_TYPE.DATE) {
    return string.substring(5, 10).replace('-', '/');
  } else if (type === TO_DATE_TYPE.TIME) {
    return string.substring(5, 16).split('T').join(' ').replace('-', '/');
  }
};

export default toDate;

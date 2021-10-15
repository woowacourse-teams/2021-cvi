import { useState } from 'react';
import { Loading } from '../components/@common';

const useLoading = () => {
  // 이게 최선인가.. loading의 추가저인 props를 어떻게 넘길 수 있을까
  const [isLoading, setIsLoading] = useState(false);

  const showLoading = () => setIsLoading(true);

  const hideLoading = () => setIsLoading(false);

  return { showLoading, hideLoading, isLoading, Loading };
};

export default useLoading;

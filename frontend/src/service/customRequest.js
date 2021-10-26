import { RESPONSE_STATE } from '../constants';

const customRequest = async (callback) => {
  try {
    const response = await callback();

    if (!response.ok) {
      throw new Error(await response.text());
    }

    const data = await response.text();

    return { state: RESPONSE_STATE.SUCCESS, data: data ? JSON.parse(data) : null };
  } catch (error) {
    const errorMessage = JSON.parse(error.message).message;
    console.error(errorMessage ?? error);

    return { state: RESPONSE_STATE.FAILURE, data: errorMessage ?? error };
  }
};

export default customRequest;

import { Button, LottieAnimation } from '../../components/common';
import { BUTTON_SIZE_TYPE } from '../../components/common/Button/Button.styles';
import {
  LoadingContainer,
  ImageContainer,
  LottieContainer,
  buttonStyles,
  Container,
  Title,
  Content,
  Image,
} from './MyPageShotVerification.styles';
import exampleImg from '../../assets/images/vaccination_example.png';
import Tesseract from 'tesseract.js';
import ImageUpLoader from 'react-images-upload';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { putAccountAsync } from '../../service';
import { AGE_RANGE, ALERT_MESSAGE, RESPONSE_STATE, SNACKBAR_MESSAGE } from '../../constants';
import { getMyInfoAsync } from '../../redux/authSlice';
import { useLoading, useSnackBar } from '../../hooks';
import { ShotVerificationCompletionHeart } from '../../assets/lotties';

const MyPageShotVerification = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer.user);
  const accessToken = useSelector((state) => state.authReducer.accessToken);

  const [fileUrl, setFileUrl] = useState('');

  const { showLoading, hideLoading, isLoading, Loading } = useLoading();
  const { openSnackBar } = useSnackBar();

  const loadPicture = (_, pictureUrl) => {
    setFileUrl(pictureUrl[0]);
  };

  const editShotVerification = async () => {
    if (!Object.keys(user).length) return;

    const { nickname, ageRange, socialId, socialProvider, socialProfileUrl } = user;
    const data = {
      nickname,
      ageRange: AGE_RANGE[ageRange?.meaning],
      socialId,
      socialProvider,
      socialProfileUrl,
      shotVerified: true,
    };

    const response = await putAccountAsync(accessToken, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT_SHOT_VERIFICATION);

      return;
    }

    dispatch(getMyInfoAsync(accessToken));

    openSnackBar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_SHOT_VERIFICATION);
  };

  const clickVerificationButton = () => {
    showLoading();

    // ocr 실행 로직
    Tesseract.recognize(fileUrl, 'kor')
      .then(({ data: { text } }) => {
        // 검증하는 로직
        const response = checkInfo(text);

        // 잘못된 사진 실패 로직
        if (response.state === RESPONSE_STATE.FAILURE) {
          alert(ALERT_MESSAGE.FAIL_TO_SHOT_VERIFICATION);

          return;
        }

        editShotVerification();
      })
      .then(() => {
        hideLoading();
        setFileUrl('');
      });
  };

  const checkInfo = (text) =>
    text.includes('님 은 코 로 나 19 백 신 1차 접 종') ||
    (text.includes('예 방 접 종 증 명 서') && text.includes('질 병 관 리청'))
      ? { state: RESPONSE_STATE.SUCCESS }
      : { state: RESPONSE_STATE.FAILURE };

  return (
    <Container>
      {isLoading ? (
        <LoadingContainer>
          <Loading isLoading={isLoading} />
          <div>약 20초 정도의 시간이 소요됩니다</div>
        </LoadingContainer>
      ) : (
        <>
          <Title>접종 인증</Title>
          {user.shotVerified ? (
            <LottieContainer>
              <LottieAnimation
                data={ShotVerificationCompletionHeart}
                width="30rem"
                description="인증 완료된 사용자입니다"
              />
            </LottieContainer>
          ) : (
            <>
              <Content>
                <Image src={exampleImg} />
                <ImageContainer isFileSelected={!!fileUrl}>
                  <div>백신 접종을 인증할 수 있는 사진을 올려주세요</div>
                  <ImageUpLoader
                    withIcon={false}
                    buttonText=""
                    imgExtension={['.jpg', '.jpeg', '.png']}
                    maxFileSize={5242880}
                    label=""
                    fileTypeError=" jpg, jpeg, png 확장자만 가능합니다"
                    fileSizeError=" 5MB 이하의 파일만 가능합니다"
                    onChange={loadPicture}
                  />
                </ImageContainer>
              </Content>
              <Button
                sizeType={BUTTON_SIZE_TYPE.LARGE}
                styles={buttonStyles}
                onClick={clickVerificationButton}
              >
                인증하기
              </Button>
            </>
          )}
        </>
      )}
    </Container>
  );
};

export default MyPageShotVerification;

# 로그 파일 경로 설정
$logFilePath = "C:\Users\sbl\programming\IdeaProjects\hanghaero\myresource\jenkins\send-property-to-jenkins-log.txt"

# 로그 기록
Start-Transcript -Path $logFilePath -Append

# jenkins ec2 private key 경로
$privateKey = "C:\Users\sbl\Desktop\aws\jenkins-ssh-key.pem"

# jenkins ec2 정보
$remoteServer = "ec2-52-78-69-20.ap-northeast-2.compute.amazonaws.com" # dns 주소
$remoteUser = "ubuntu" # 접속할 user명
$projectName = "hh" # 프로젝트명, jenkins의 item 명과 같다.
$remotePath = "/jenkins/workspace/$projectName/src/main/resources/" # property 파일이 복사되어야 할 디렉토리
$pemRemotePath = "/jenkins/workspace/keys/$projectName" # deploy ec2에 접속 가능한 pem

# 내 컴퓨터에서 git ignore에 추가된 properties 경로들 {dev, local, secret, deploy}
$localFiles = @(
    "C:\Users\sbl\programming\IdeaProjects\hanghaero\src\main\resources\application-deploy.yml",
    "C:\Users\sbl\programming\IdeaProjects\hanghaero\src\main\resources\application-local.yml",
    "C:\Users\sbl\programming\IdeaProjects\hanghaero\src\main\resources\application-secret.yml",
    "C:\Users\sbl\programming\IdeaProjects\hanghaero\src\main\resources\application-dev.yml"
)

$pemFile = "C:\Users\sbl\Desktop\aws\EC2_freetier_keypair\ec2freetierkeypair.pem"

# 각 로컬 파일에 대해 scp 명령 실행
foreach ($localFile in $localFiles)
{
    $scpCommand = "scp -i '$privateKey' '$localFile' $remoteUser@$( $remoteServer ):$( $remotePath )" #remoteServer 변수 사용간 @$remoteServer로 사용하면 해시테이블 표기법이 된다 주의, $($변수명) 문법은 변수를 정확히 표현하기 위함
    Write-Output $scpCommand
    Invoke-Expression $scpCommand
}

# pem파일 전송
$createDirCommand = "ssh -i '$privateKey' $remoteUser@$( $remoteServer ) 'sudo mkdir -p $pemRemotePath'"
Write-Output $createDirCommand
Invoke-Expression $createDirCommand

# pem파일이 복사될 폴더 권한 설정
$chmodCommand = "ssh -i '$privateKey' $remoteUser@$( $remoteServer ) 'sudo chmod 777 $pemRemotePath'"
Write-Output $chmodCommand
Invoke-Expression $chmodCommand

$scpCommand = "scp -i '$privateKey' '$pemFile' $remoteUser@$( $remoteServer ):$( $pemRemotePath )"
Write-Output $scpCommand
Invoke-Expression $scpCommand

#로그 기록 종료
Stop-Transcript

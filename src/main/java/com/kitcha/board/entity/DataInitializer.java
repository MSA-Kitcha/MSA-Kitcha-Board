package com.kitcha.board.entity;

import com.kitcha.board.repository.BoardRepository;
import com.kitcha.board.repository.FileRepository;
import com.kitcha.board.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (boardRepository.count() == 0 && fileRepository.count() == 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

            // 1번 데이터
            Board board1 = new Board();
            board1.setBoardTitle("국립금오공대, 안전관리 우수연구실 3곳 인증받았네요");
            board1.setContent("국립금오공대가 '2024 안전관리 우수연구실'로 3개 연구실을 인증받은 것은 매우 긍정적인 소식입니다. 연구실 안전은 그 어느 때보다 중요한 문제인데, 고분자재료실험실, 미세구조제어연구실, 의료융합실험실이 안전관리 기준을 충족하며 인증을 받은 점은 연구 환경의 안전성을 높이는 중요한 계기가 될 것입니다. 이러한 인증 제도와 인센티브 제도는 안전문화 정착에 큰 도움이 될 것입니다.");
            board1.setCreatedAt(LocalDateTime.parse("2025-02-26 17:27:22.939212", formatter));
            board1.setDeletedYn(false);
            board1.setHitCnt(1);
            board1.setLongSummary("국립금오공과대학교 3개의 연구실이 '2024 안전관리 우수연구실'로 인증됐습니다. 고분자재료실험실, 미세구조제어연구실, 의료융합실험실이 신규 인증된 곳입니다. '안전관리 우수연구실 인증'은 관련 전문가 심사를 통해 안전관리 활동과 수준을 인증하는 제도입니다. 인증기간은 2년으로, 연구실 안전환경, 활동 수준, 관계자 안전의식을 평가합니다. 국립금오공대는 2016년부터 이 인증을 받고 있으며, 올해에는 인센티브 제도를 마련해 안전관리 우수연구실 인증제 표창식을 진행했습니다. 안전팀을 설립해 안전한 연구실 환경을 조성하는 계획을 가지고 있습니다.");
            board1.setNewsTitle("국립금오공대, '2024 안전관리 우수연구실' 3곳 인증");
            board1.setNewsUrl("https://news.unn.net/news/articleView.html?idxno=575580");
            board1.setNickname("juyojeon");
            board1.setUserId(4L);

            // 2번 데이터
            Board board2 = new Board();
            board2.setBoardTitle("공공SW 대기업 참여제한 논란, 찬반 의견 엇갈려");
            board2.setContent("‘공공SW 대기업 참여제한’에 대한 논의가 여전히 뜨겁습니다. 대기업 참여를 지지하는 목소리는 공공 사업의 품질과 기술력 향상을 기대하는 반면, 행정 편의성을 중시하는 입장에서는 효율적 관리와 예산 절감을 우선시하고 있습니다. 양측의 입장이 상반되지만, 결국 공공의 이익을 최우선으로 고려한 합리적인 접근이 필요할 것입니다.");
            board2.setCreatedAt(LocalDateTime.parse("2025-02-26 17:26:26.660250", formatter));
            board2.setDeletedYn(false);
            board2.setHitCnt(2);
            board2.setLongSummary("2월 26일, '공공SW 대기업 참여제한'에 대한 찬반 논의가 지속되고 있다. 서울 여의도 국회의원회관에서 개최된 '공공SI 편의성 개선방안 마련 토론회'에서 참석자들은 대기업의 참여가 필요하다고 주장했다. 반면, 행정부의 관점에서는 행정편의 발상이 필요하다고 보고 있다.");
            board2.setNewsTitle("‘공공SW 대기업 참여제한’ 찬반 여전…“행정편의 발상” vs. “대기업...");
            board2.setNewsUrl("https://n.news.naver.com/mnews/article/138/0002191614?sid=105");
            board2.setNickname("kmj");
            board2.setUserId(5L);

            // 3번 데이터
            Board board3 = new Board();
            board3.setBoardTitle("삼성증권, 알고리즘 투자 서비스 '굴링', 11만 명 돌파하며 인기 상승");
            board3.setContent("알고리즘 기반 투자 서비스가 빠르게 확산 중입니다. 삼성증권 ‘굴링’은 높은 가입자 증가율과 코스피 대비 우수한 수익률로 주목받고 있습니다. 자동화된 포트폴리오 추천이 시장 대응력을 높이는 점은 긍정적이지만, 투자자는 리스크를 인지하고 신중하게 활용해야 합니다. 앞으로 더 발전된 서비스로 투자자들에게 안정적인 기회를 제공하길 기대합니다.");
            board3.setCreatedAt(LocalDateTime.parse("2025-02-26 16:59:20.200546", formatter));
            board3.setDeletedYn(false);
            board3.setHitCnt(1);
            board3.setLongSummary("삼성증권의 알고리즘 기반 포트폴리오 추천 서비스 '굴링'이 지난달까지 누적 가입자 수 11만9000명을 기록했다. 이는 전년 동기 대비 60% 증가한 것으로, 굴링이 효과적인 투자 전략으로 선택받고 있음을 보여준다. 고객의 투자 목표와 성향에 맞는 포트폴리오를 추천해주는 로보·연금·주식 굴링이 제공된다. 이러한 서비스를 통해 고객은 유연하게 시장 변화에 대응할 수 있으며, 목표 달성을 돕는 사후 관리 서비스도 제공받을 수 있다. 삼성증권의 로보굴링은 지난달 31일 기준 6개월 누적 수익률은 6.43%로, 코스피 수익률의 -5.93%를 초과했다. 로보굴링 및 연금굴링 투자 고객을 대상으로 순입금 및 투자 기준을 충족하면 상품권을 받을 수 있는 이벤트도 진행 중이다.");
            board3.setNewsTitle("삼성증권, 알고리즘 기반 '굴링서비스' 이용자 11만명 돌파");
            board3.setNewsUrl("http://www.seoulwire.com/news/articleView.html?idxno=640059");
            board3.setNickname("seula");
            board3.setUserId(3L);

            // 4번 데이터
            Board board4 = new Board();
            board4.setBoardTitle("아디다스, 서울시와 손잡고 스포츠 평등 실현");
            board4.setContent("아디다스가 서울시와 협력해 풀뿌리 스포츠 지원을 확대하는 것은 매우 의미 있는 행보입니다. 특히 청소년 스포츠 인재 육성을 위한 기부와 훈련 시설 지원이 주목됩니다. 스포츠가 모든 사람에게 평등한 기회를 제공해야 한다는 취지에 공감하며, 앞으로도 지속적인 지원이 이어지길 기대합니다.");
            board4.setCreatedAt(LocalDateTime.parse("2025-02-26 17:05:41.824887", formatter));
            board4.setDeletedYn(false);
            board4.setHitCnt(3);
            board4.setLongSummary("아디다스코리아가 서울시와 협력해 '서울아 운동하자' 사회공헌사업의 일환으로 5개 서울 스포츠팀에 기부금을 전달했다. 이 중 서울체육중학교 육상부에는 육상 인재 육성을 위한 훈련 시설과 용품 지원을 계획했다. 아디다스는 '모두에게 평등한 스포츠 세상'이라는 사회공헌 목표를 위해 전 세계에 다양한 프로젝트를 진행하고 있다. 피터곽 대표이사는 국내 풀뿌리 스포츠에 대한 투자와 지원을 강화하며, 스포츠가 모든 이들에게 동등한 기회를 제공하고 일상에서 건강하게 펼쳐질 수 있도록 한국 사회에 기여할 계획이다.");
            board4.setNewsTitle("아디다스, 서울시와 '모두에게 평등한 스포츠 세상' 만든다… 풀뿌리 스...");
            board4.setNewsUrl("http://www.hansbiz.co.kr/news/articleView.html?idxno=736184");
            board4.setNickname("seula");
            board4.setUserId(3L);

            // 5번 데이터
            Board board5 = new Board();
            board5.setBoardTitle("삼성증권 ‘굴링’ 서비스, 11만 명 돌파 ??");
            board5.setContent("알고리즘 기반 투자 서비스가 빠르게 확산 중입니다. 삼성증권 ‘굴링’은 높은 가입자 증가율과 코스피 대비 우수한 수익률로 주목받고 있습니다. 자동화된 포트폴리오 추천이 시장 대응력을 높이는 점은 긍정적이지만, 투자자는 리스크를 인지하고 신중하게 활용해야 합니다. 앞으로 더 발전된 서비스로 투자자들에게 안정적인 기회를 제공하길 기대합니다.");
            board5.setCreatedAt(LocalDateTime.parse("2025-02-26 16:59:20.200546", formatter));
            board5.setDeletedYn(false);
            board5.setHitCnt(9);
            board5.setLongSummary("삼성증권의 알고리즘 기반 포트폴리오 추천 서비스 '굴링'이 지난달까지 누적 가입자 수 11만9000명을 기록했다. 이는 전년 동기 대비 60% 증가한 것으로, 굴링이 효과적인 투자 전략으로 선택받고 있음을 보여준다. 고객의 투자 목표와 성향에 맞는 포트폴리오를 추천해주는 로보·연금·주식 굴링이 제공된다. 이러한 서비스를 통해 고객은 유연하게 시장 변화에 대응할 수 있으며, 목표 달성을 돕는 사후 관리 서비스도 제공받을 수 있다. 삼성증권의 로보굴링은 지난달 31일 기준 6개월 누적 수익률은 6.43%로, 코스피 수익률의 -5.93%를 초과했다. 로보굴링 및 연금굴링 투자 고객을 대상으로 순입금 및 투자 기준을 충족하면 상품권을 받을 수 있는 이벤트도 진행 중이다.");
            board5.setNewsTitle("삼성증권, 알고리즘 기반 '굴링서비스' 이용자 11만명 돌파");
            board5.setNewsUrl("http://www.seoulwire.com/news/articleView.html?idxno=640059");
            board5.setNickname("suyeon");
            board5.setUserId(2L);

            // 6번 데이터
            Board board6 = new Board();
            board6.setBoardTitle("데이터 보안은 어떻게?!!");
            board6.setContent("개인정보 유출 위험이 여전한 상황에서 딥시크 금지를 해제하자는 중국의 요구는 신중한 검토가 필요합니다. 보안과 경제적 이익 사이에서 균형을 맞추는 것이 중요하며, 기업과 정부 모두 데이터 보호를 최우선으로 고려해야 할 것입니다");
            board6.setCreatedAt(LocalDateTime.parse("2025-02-26 18:05:37.804758", formatter));
            board6.setDeletedYn(false);
            board6.setHitCnt(9);
            board6.setLongSummary("딥시크, 중국 대사가 데이터 유출 위험에 대한 우려를 표하면서 금지 풀기를 요청했습니다. 김희수 기자와 김태성 기자가 26일 김원기 대사관에서 중국 대사 김원기와 인터뷰를 진행하면서 정보 저장 조항에 대한 논의를 했다. 김희수 기자와 김태성 기자는 \"중국에 데이터 제공에 대한 적이 없다\"며, \"기업의 보안에 대한 우려가 있다\"고 말했습니다. 이에 대해, 김원기 대사는 \"딥시크의 금지 해제를 요구한다\"고 말했습니다. 이로인해, 중국 대사가 딥시크 금지 해제를 요구하면서, 데이터 유출 위험에 대한 우려를 표하고 있습니다.");
            board6.setNewsTitle("개인정보 유출 위험 여전한데 … 딥시크 금지령 풀어달라는 중");
            board6.setNewsUrl("https://n.news.naver.com/mnews/article/009/0005450536?sid=105");
            board6.setNickname("jinsil");
            board6.setUserId(1L);

            // 7번 데이터
            Board board7 = new Board();
            board7.setBoardTitle("보안 우려는 해결됐을까?");
            board7.setContent("딥시크 OS와 관련된 보안 우려가 계속 제기되는 상황에서 금지 해제 논의가 이루어지는 것은 신중한 접근이 필요합니다. 기업과 사용자의 데이터 보호 대책이 충분히 마련되지 않은 상태에서 규제 완화는 오히려 더 큰 위험을 초래할 수 있습니다. 보안 강화 방안이 먼저 선행되어야 합니다.");
            board7.setCreatedAt(LocalDateTime.parse("2025-02-26 18:39:34.848385", formatter));
            board7.setDeletedYn(false);
            board7.setHitCnt(3);
            board7.setLongSummary("중국에서 딥시크(Deepin) OS를 사용하는 사람들이 정보 저장 조항으로 인해 불안감을 토로했다. 이들은 중국 정부가 기업에 대한 기업 데이터 수집을 촉구하는 것이 아니라고 주장했다. IT 업계에서는 이 사실을 알게 된 후 보안 우려 목소리가 제기됐다.");
            board7.setNewsTitle("개인정보 유출 위험 여전한데 … 딥시크 금지령 풀어달라는 중");
            board7.setNewsUrl("https://n.news.naver.com/mnews/article/009/0005450536?sid=105");
            board7.setNickname("suyeon");
            board7.setUserId(2L);

            // 8번 데이터
            Board board8 = new Board();
            board8.setBoardTitle("이차전지 혁신!");
            board8.setContent("충·방전 속도와 수명을 개선하면서도 크기와 무게를 줄이고, 제조 비용까지 절감할 수 있다니 상용화된다면 전기차, ESS 등 다양한 분야에서 혁신을 가져올 것 같네요. 앞으로 이 기술이 어떻게 발전할지 기대됩니다!");
            board8.setCreatedAt(LocalDateTime.parse("2025-02-26 19:37:12.220127", formatter));
            board8.setDeletedYn(false);
            board8.setHitCnt(17);
            board8.setLongSummary("한국기계연구원 연구팀이 세계 최초로 롤투롤(Roll-to-Roll) 공정과 호환되는 플래시(Flash) 활성화 기술을 개발했다. 이 기술은 배터리의 에너지 밀도와 용량을 향상시키며, 크기와 무게를 줄이고 제조 비용을 절감할 수 있는 획기적인 가능성을 제시한다. 이 기술은 초고속·대면적 플래시 공정을 활용해 후막 전극의 열화를 억제하는 전극 활성화 기술이다. 이를 통해 전극의 두께를 늘려 고에너지 배터리를 만들어도 충·방전 속도와 수명이 저하되는 문제를 해결할 수 있다. 이 연구는 전기화학적 성능을 향상시키고, 배터리 생산 비용을 절감하는 데 큰 의미가 있다.\n\n*율속특성(Rate Capability): 배터리의 충방전 속도\n**광열 반응(Photothermal Reaction): 광선에 의한 열 반응");
            board8.setNewsTitle("기계연, 롤투롤 호환 플래시 공정 기술로 이차전지 성능 혁신");
            board8.setNewsUrl("http://www.breaknews.com/1095848");
            board8.setNickname("jinsil");
            board8.setUserId(1L);
            boardRepository.save(board1);
            boardRepository.save(board2);
            boardRepository.save(board3);
            boardRepository.save(board4);
            boardRepository.save(board5);
            boardRepository.save(board6);
            boardRepository.save(board7);
            boardRepository.save(board8);

            fileService.createPdf(board1);
            fileService.createPdf(board2);
            fileService.createPdf(board3);
            fileService.createPdf(board4);
            fileService.createPdf(board5);
            fileService.createPdf(board6);
            fileService.createPdf(board7);
            fileService.createPdf(board8);
        }
    }
}

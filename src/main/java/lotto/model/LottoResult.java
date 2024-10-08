package lotto.model;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class LottoResult {

    private static final int FIRST_PRIZE_COUNT = 6;
    private static final int SECOND_PRIZE_COUNT = 5;
    private static final int THIRD_PRIZE_COUNT = 5;
    private static final int FOURTH_PRIZE_COUNT = 4;
    private static final int FIFTH_PRIZE_COUNT = 3;

    private static final int FIRST_PRIZE_AMOUNT = 2_000_000_000;
    private static final int SECOND_PRIZE_AMOUNT = 30_000_000;
    private static final int THIRD_PRIZE_AMOUNT = 1_500_000;
    private static final int FOURTH_PRIZE_AMOUNT = 50_000;
    private static final int FIFTH_PRIZE_AMOUNT = 5_000;

    private static final String THREE_MATCH = "3개 일치";
    private static final String FOUR_MATCH = "4개 일치";
    private static final String FIVE_MATCH = "5개 일치";
    private static final String FIVE_MATCH_BONUS = "5개 일치, 보너스 볼 일치";
    private static final String SIX_MATCH = "6개 일치";

    private final Map<String, Integer> prizeCounts = new LinkedHashMap<>();

    public LottoResult() {
        prizeCounts.put(THREE_MATCH, 0);
        prizeCounts.put(FOUR_MATCH, 0);
        prizeCounts.put(FIVE_MATCH, 0);
        prizeCounts.put(FIVE_MATCH_BONUS, 0);
        prizeCounts.put(SIX_MATCH, 0);
    }

    public void updateResult(int matchCount, boolean hasBonus) {
        if (matchCount == FIRST_PRIZE_COUNT) {
            prizeCounts.put(SIX_MATCH, prizeCounts.get(SIX_MATCH) + 1);
            return;
        }

        if (matchCount == SECOND_PRIZE_COUNT && hasBonus) {
            prizeCounts.put(FIVE_MATCH_BONUS, prizeCounts.get(FIVE_MATCH_BONUS) + 1);
            return;
        }

        if (matchCount == SECOND_PRIZE_COUNT) {
            prizeCounts.put(FIVE_MATCH, prizeCounts.get(FIVE_MATCH) + 1);
            return;
        }

        if (matchCount == FOURTH_PRIZE_COUNT) {
            prizeCounts.put(FOUR_MATCH, prizeCounts.get(FOUR_MATCH) + 1);
            return;
        }

        if (matchCount == FIFTH_PRIZE_COUNT) {
            prizeCounts.put(THREE_MATCH, prizeCounts.get(THREE_MATCH) + 1);
        }
    }

    public void printResult() {
        System.out.println("당첨 통계");
        System.out.println("----");
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // 상금 및 개수 출력
        for (String prize : prizeCounts.keySet()) {
            int count = prizeCounts.get(prize);
            int prizeAmount = getPrizeAmount(prize); // 해당 상금 가져오기
            String formattedPrizeAmount = decimalFormat.format(prizeAmount); // 상금을 쉼표가 있는 형식으로 변환
            System.out.println(prize + " (" + formattedPrizeAmount + "원) - " + count + "개");
        }
    }

    public int getPrizeAmount(String prize) {
        switch (prize) {
            case SIX_MATCH:
                return FIRST_PRIZE_AMOUNT;
            case FIVE_MATCH_BONUS:
                return SECOND_PRIZE_AMOUNT;
            case FIVE_MATCH:
                return THIRD_PRIZE_AMOUNT;
            case FOUR_MATCH:
                return FOURTH_PRIZE_AMOUNT;
            case THREE_MATCH:
                return FIFTH_PRIZE_AMOUNT;
            default:
                return 0;
        }
    }

    public double calculateYield(int totalAmountSpent) {
        int totalPrize = prizeCounts.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * getPrizeAmount(entry.getKey()))
                .sum();
        return Math.round((totalPrize / (double) totalAmountSpent) * 10000.0) / 100.0; // 소수점 둘째 자리에서 반올림
    }
}

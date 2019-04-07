package programmers;

class Solution {
    public int[] solution(int[] prices) {
        int len =prices.length;
        int[] answer = new int[len];
        for(int i =0; i<prices.length;i++)
        {
            answer[i]=0;
            for(int j=i+1;j<prices.length;j++)
            {
                if(prices[i]<=prices[j]){
                    answer[i]++;
                }
            }
            
        }

        return answer;
    }
    public static void main(String[] args) {
        Solution sol =new Solution();
        int[] prices={1,2,3,2,3};
        int[] answer =new int[5];
        answer=sol.solution(prices);
        for(int i=0;i<5;i++){
            System.out.print(answer[i]+" ");
        }
    }

}
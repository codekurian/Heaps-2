class Problem1 {
    public int[] topKFrequent(int[] nums, int k) {
        return topKUsingMap(nums,k);
    }

    //using heap 
    //TC:O(nlogk)
    public int[] topKUsingHeap (int[] nums, int k){
        Map<Integer,Integer> freqMap = new HashMap<>();
        //min heap
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);
        //build freqmap
        for(int i=0;i<nums.length;i++){
            int num = nums[i];
            int count = freqMap.getOrDefault(num, 0);
            freqMap.put(num,count+1);

        }

        for(int key : freqMap.keySet()){
            if(pq.size()==k){
                if( pq.peek()[0]<freqMap.get(key)){
                    pq.poll();
                    pq.offer(new int[]{freqMap.get(key),key});
                }


            }else{
                pq.offer(new int[]{freqMap.get(key),key});
            }


        }

        int[] result = new int[k];
        int j=0;
        while(!pq.isEmpty()&& j<k){
            result[j] = pq.poll()[1];
            j++;
        }

        return result;

    }
    //using
    //TC:O(N)
    //SC:O(N)
    public int[] topKUsingMap (int[] nums, int k){
        Map<Integer,Integer> freqMap = new HashMap<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        //build freqmap
        for(int i=0;i<nums.length;i++){
            int num = nums[i];
            int count = freqMap.getOrDefault(num, 0);
            freqMap.put(num,count+1);

        }

        Map<Integer,List<Integer>> freqToNumbers = new HashMap<>();
        for(int key : freqMap.keySet()){
            freqToNumbers.putIfAbsent(freqMap.get(key), new ArrayList<>());
            List<Integer> numList =  freqToNumbers.get(freqMap.get(key));
            numList.add(key);
            min = Math.min(min,freqMap.get(key));
            max = Math.max(max,freqMap.get(key));
        }


        int[] result = new int[k];
        int idx=0;
        for(int i=max;i>=min && idx<k ;i--){
            if(!freqToNumbers.containsKey(i)) continue;
            List<Integer> numList = freqToNumbers.get(i);
            for(int j=0;j<numList.size() && idx<k;j++){
                result[idx] = numList.get(j);
                idx++;
            }
        }

        return result;
    }


    //
    public int[] topKUsingQuickSelect (int[] nums, int k){

        int[] result = new int[k];
        int index = 0;
        for(int i=nums.length-1;i>i-k && i>0;i--){
            result[index++] =  quickSelect (0,nums.length-1,nums,i);

        }
        return result;
    }

    int quickSelect (int l,int r,int [] nums,int x){
        int pivot = r;
        int p =l;
        for(int i=l;i<r;i++){
            if(nums[i]<nums[pivot]){
                swap(i,pivot,nums);
                p++;
            }
        }
        swap(p,pivot,nums);
        if(p<x){
            return quickSelect (l,p-1,nums,x);
        }else if(p>x){
            return  quickSelect (p+1,r,nums,x);
        }else{
            return nums[x];
        }
    }

    void swap(int from,int to ,int[] nums){
        int num = nums[from];
        nums[from] = nums[to];
        nums[to] = num;
    }
}
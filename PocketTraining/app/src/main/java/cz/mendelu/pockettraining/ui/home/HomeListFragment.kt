package cz.mendelu.pockettraining.ui.home

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.databinding.FragmentHomeListBinding
import cz.mendelu.pockettraining.databinding.RowHomeListBinding
import cz.mendelu.pockettraining.model.UsedExercise
import kotlinx.coroutines.launch


class HomeListFragment :
    BaseFragment<FragmentHomeListBinding, HomeListViewModel>(HomeListViewModel::class) {
    private val arguments: HomeListFragmentArgs by navArgs()

    private val usedExerciseList: MutableList<UsedExercise> = mutableListOf()


    private lateinit var adapter: PlanAdapter
    private lateinit var layoutManager: LinearLayoutManager

    //diff
    inner class PlanDiffUtils(private val oldList: MutableList<UsedExercise>,
                              private val newList: MutableList<UsedExercise>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].pause == newList[newItemPosition].pause
                    && oldList[oldItemPosition].sets == newList[newItemPosition].sets
                    && oldList[oldItemPosition].reps == newList[newItemPosition].reps

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].usedExerciseName == newList[newItemPosition].usedExerciseName
        }
    }
    //adapter
    inner class PlanAdapter : RecyclerView.Adapter<PlanAdapter.ExerciseViewHolder>(){

        inner class ExerciseViewHolder(val binding: RowHomeListBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            return ExerciseViewHolder(
                RowHomeListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {

            holder.binding.UsedExerciseName.text = usedExerciseList[position].usedExerciseName
            //holder.binding.
            holder.binding.pause.text = usedExerciseList[position].pause.toString()
            holder.binding.reps.text = usedExerciseList[position].reps.toString()
            holder.binding.sets.text = usedExerciseList[position].sets.toString()


        }
        override fun getItemCount(): Int = usedExerciseList.size
    }



    override val bindingInflater: (LayoutInflater) -> FragmentHomeListBinding
        get() = FragmentHomeListBinding::inflate


    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = PlanAdapter()
        binding.usedExerciseList.layoutManager = layoutManager
        binding.usedExerciseList.adapter = adapter

            viewModel.id = if(arguments.planId != -1L) arguments.planId
            else null
            if(viewModel.id == null){

            }else{



            viewModel
                .getUsedExercise()
                .observe(viewLifecycleOwner, object : Observer<MutableList<UsedExercise>>
                {
                    override fun onChanged(t: MutableList<UsedExercise>?){
                        val diffCallback = PlanDiffUtils(usedExerciseList, t!!)
                        val diffResult = DiffUtil.calculateDiff(diffCallback)
                        diffResult.dispatchUpdatesTo(adapter)
                        usedExerciseList.clear()
                        usedExerciseList.addAll(t)
                    }

                })
                lifecycleScope.launch {
                    viewModel.plan = viewModel.findPlanById()
                }.invokeOnCompletion {
                }

            }}


    override fun onActivityCreated() {
    }

}
package cz.mendelu.pockettraining.ui.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.databinding.FragmentExerciseListBinding
import cz.mendelu.pockettraining.databinding.RowExerciseListBinding
import cz.mendelu.pockettraining.model.Exercise


class ExerciseListFragment :
    BaseFragment<FragmentExerciseListBinding, ExerciseListViewModel>(ExerciseListViewModel::class){

    override val bindingInflater: (LayoutInflater) -> FragmentExerciseListBinding
        get() = FragmentExerciseListBinding::inflate


    private val exerciseList: MutableList<Exercise> = mutableListOf()
        private lateinit var adapter: ExercisesAdapter
        private lateinit var layoutManager: LinearLayoutManager

        inner class ExerciseDiffUtils(private val oldList: MutableList<Exercise>,
                                      private val newList: MutableList<Exercise>) : DiffUtil.Callback(){
            override fun getOldListSize(): Int = oldList.size
            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].exerciseName == newList[newItemPosition].exerciseName
            }
        }
    //ADAPTER
    inner class ExercisesAdapter : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>(){

        inner class ExerciseViewHolder(val binding: RowExerciseListBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            return ExerciseViewHolder(
                RowExerciseListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
            val exercise = exerciseList.get(position)
            holder.binding.exerciseName.text = exercise.exerciseName
            
            holder.binding.root.setOnClickListener { 
                val action = ExerciseListFragmentDirections.actionExerciseFragmentToAddExercise()
                action.id = exerciseList.get(holder.adapterPosition).id!!
                findNavController().navigate(action)
            }
        }

        override fun getItemCount(): Int = exerciseList.size
    }

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = ExercisesAdapter()
        binding.exerciseList.layoutManager = layoutManager
        binding.exerciseList.adapter = adapter

       viewModel
            .getAll()
            .observe(viewLifecycleOwner, object : Observer<MutableList<Exercise>>
            {
                override fun onChanged(t: MutableList<Exercise>?){
                   val diffCallback = ExerciseDiffUtils(exerciseList, t!!)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter)
                    exerciseList.clear()
                    exerciseList.addAll(t!!)
                }

            })
        binding.fabExercise.setOnClickListener{
            findNavController().navigate(ExerciseListFragmentDirections.actionExerciseFragmentToAddExercise())
        }
    }

    override fun onActivityCreated() {}
    }